#!/usr/bin/env bash

# cat <<EOF > /etc/kafka/server.properties.custom
# node.id=${KAFKA_NODE_ID}
# process.roles=${KAFKA_PROCESS_ROLES}
# controller.quorum.voters=${KAFKA_CONTROLLER_QUORUM_VOTERS}
# listeners=${KAFKA_LISTENERS}
# advertised.listeners=${KAFKA_ADVERTISED_LISTENERS}
# listener.security.protocol.map=${KAFKA_LISTENER_SECURITY_PROTOCOL_MAP}
# controller.listener.names=${KAFKA_CONTROLLER_LISTENER_NAMES}
# offsets.topic.replication.factor=${KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR}
# EOF
      
if [ ! -f /var/lib/kafka/data/meta.properties ]; then
    uuid=$(kafka-storage random-uuid)
    kafka-storage format --cluster-id $uuid --config /etc/kafka/server.properties.custom
fi

kafka-server-start /etc/kafka/server.properties.custom