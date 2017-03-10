#!/bin/bash

docker stop $(docker ps -a -q)
docker ps
docker-compose up -d
docker ps
