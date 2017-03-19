#!/bin/bash

echo "Stopping running docker containers"
docker ps -q | xargs -r docker stop  || exit 1

echo "Restoring backup data"
rm -rf apolodoro-elasticsearch/esdata1/*                                           &&
tar -xzf apolodoro-elasticsearch/esdata1.tar.gz -C apolodoro-elasticsearch/esdata1 || exit 1

echo "Starting Apolodoro docker containers"
docker-compose up -d || exit 1

echo "Running docker containers :"
docker ps

echo "Go to http://localhost/"