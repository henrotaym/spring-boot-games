locals {
  proxy_stack_target_location = "/home/ubuntu/apps/proxy/docker-compose.yml"
}

data "doppler_secrets" "server" {
  project = "oci-${var.SERVER_NAME}"
  config = "private"
}

resource "doppler_service_token" "proxy" {
  project = data.doppler_secrets.server.project
  config = "proxy"
  name = "proxy stack"
}

resource "ssh_resource" "deploy_proxy_stack" {
  triggers = {
    always_run = "${timestamp()}"
  }
  host = data.doppler_secrets.server.map.PUBLIC_IP
  user = data.doppler_secrets.server.map.SSH_USERNAME
  private_key = data.doppler_secrets.server.map.SSH_PRIVATE_KEY
  timeout = "1m"
  file {
    content = file("stacks/proxy/docker-compose.yml")
    destination = local.proxy_stack_target_location
  }
  commands = [
    "doppler run --token=${doppler_service_token.proxy.key} -- docker stack deploy -c ${local.proxy_stack_target_location} --detach=false proxy"
  ]
}
