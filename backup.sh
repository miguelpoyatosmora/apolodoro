#!/usr/bin/env bash

echo "Stopping running docker containers"
docker ps -q | xargs -r docker stop                                                 || exit 1

echo "Creating backup"
cd apolodoro-elasticsearch/esdata1/ && tar cvzf ../esdata1.tar.gz . && cd ../..     || exit 1