#!/bin/bash

echo "Building npm dependencies"
cd apolodoro-react-frontend
npm run build
cd ..

echo "Building maven dependencies"
mvn clean package -T 8      || exit 1

echo "Building docker containers"
docker-compose build        || exit 1
