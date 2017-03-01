#!/bin/bash

cd apolodoro-backend
mvn clean install -T 8

cd ..
sudo docker-compose build
