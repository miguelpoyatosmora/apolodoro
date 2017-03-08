#!/bin/bash

cd apolodoro-react-frontend
npm run build

cd ..
cd apolodoro-backend
mvn clean install -T 8

cd ..
sudo docker-compose build
