#!/usr/bin/env bash

source .env.local && \
doppler secrets download --no-file \
      --format env | grep -Ev 'DOPPLER' \
    > .env && \
echo "# DOCKER" >> .env
echo "USER_ID=$(id -u)" >> .env
echo "GROUP_ID=$(id -g)" >> .env
cat .env.local >> .env
