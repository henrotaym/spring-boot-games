#!/usr/bin/env bash

queue=""
if [ "$1" == "--queue" ]; then
  queue="-Dspring-boot.run.profiles=queue"
fi

mvn spring-boot:run $queue
