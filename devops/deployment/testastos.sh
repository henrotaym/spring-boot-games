#!/bin/bash

# A exécuter avec # Install docker on ubuntu
apt-get update
apt-get install ca-certificates curl
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
chmod a+r /etc/apt/keyrings/docker.asc
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update
apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
groupadd docker
usermod -aG docker $USER
newgrp docker

# Enable docker logs rotation to limit storage taken
tee /etc/docker/daemon.json > /dev/null <<EOF
{
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "50m",
    "max-file": "3"
  }
}
EOF
systemctl restart docker

# Network ingress & egress in oracle dashboard
# Rules addition using iptables
sudo iptables -A INPUT -p tcp --dport 2377 -j ACCEPT -m comment --comment "Docker Swarm Management"
sudo iptables -A INPUT -p tcp --dport 7946 -j ACCEPT -m comment --comment "Docker Swarm Node TCP Communication"
sudo iptables -A INPUT -p udp --dport 7946 -j ACCEPT -m comment --comment "Docker Swarm Node UDP Communication"
sudo iptables -A INPUT -p udp --dport 4789 -j ACCEPT -m comment --comment "Docker Swarm Overlay Network"
sudo iptables -A INPUT -p tcp --dport 80 -j ACCEPT -m comment --comment "HTTP Web Traffic"
sudo iptables -A INPUT -p tcp --dport 443 -j ACCEPT -m comment --comment "HTTPS Web Traffic"
sudo iptables -A INPUT -p tcp --dport 25060 -j ACCEPT -m comment --comment "Database Traffic 25060"
# TODO ouvrir port 123 pour ntp (time sync)
# { protocol: "udp", port: "123", public: true },
# iptables -A INPUT -p tcp --dport 8080 -j ACCEPT -m comment --comment "Traefik Dashboard"
sudo iptables-save | tee /etc/iptables/rules.v4

# Install doppler
apt-get update && apt-get install -y apt-transport-https ca-certificates curl gnupg && \
curl -sLf --retry 3 --tlsv1.2 --proto "=https" 'https://packages.doppler.com/public/cli/gpg.DE2A7741A397C129.key' | gpg --dearmor -o /usr/share/keyrings/doppler-archive-keyring.gpg && \
echo "deb [signed-by=/usr/share/keyrings/doppler-archive-keyring.gpg] https://packages.doppler.com/public/cli/deb/debian any-version main" | tee /etc/apt/sources.list.d/doppler-cli.list && \
apt-get update && \
apt-get install doppler

# Docker swarm initialization
PRIVATE_IP=$(ip route get 8.8.8.8 | awk -F"src " 'NR==1{split($2,a," ");print a[1]}')
docker swarm init --advertise-addr $PRIVATE_IP
# docker swarm join --token SWMTKN-1-2lgznwo7jywluqx2696d5aek8kva0f86j5ke3tl07h2lsivbtb-8vdosrg6m6y07c0f9yo2vwl0z 10.0.0.192:2377
# docker swarm join --token SWMTKN-1-07dcoztw7d7kz50cbdeypsacvkn4dkuuvuyesdat451t0snbm0-6ld6hbi61m68vtlvehmd5tbjv 10.0.0.214:2377

docker network create --driver overlay --attachable accessible

mkdir -p /home/ubuntu/apps/proxy
# TODO Copier la stack à cet endroit
docker stack deploy -c /home/ubuntu/apps/proxy/docker-compose.yml --detach=false proxy

mkdir -p /home/ubuntu/apps/spring-boot-games
# TODO Copier la stack à cet endroit
cd /home/ubuntu/apps/spring-boot-games
# TODO 
export DOPPLER_TOKEN=XXXXXXXXXXXXXXXXXXXX
doppler configure set token --scope /home/ubuntu/apps/spring-boot-games $DOPPLER_TOKEN
doppler run --token=$DOPPLER_TOKEN -- docker stack deploy -c /home/ubuntu/apps/spring-boot-games/docker-compose.yml --detach=false spring-boot-games

cd /home/ubuntu



# Action git
docker stack deploy -c /home/ubuntu/apps/proxy/docker-compose.yml --detach=false proxy

export DOPPLER_TOKEN=${{ secrets.DOPPLER_TOKEN }} IMAGE_TAG=${{ github.sha }} doppler run --token=$DOPPLER_TOKEN -- docker stack deploy -c /home/ubuntu/apps/spring-boot-games/docker-compose.yml --detach=false spring-boot-games

