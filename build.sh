#!/bin/bash

echo "Building npm dependencies"
cd apolodoro-react-frontend
npm install                                                                 &&
react-scripts --version >/dev/null 2>&1 || npm install -g react-scripts     &&
npm run build                                                               || exit 1
cd ..

echo "Building maven dependencies"
mvn clean package -T 8                                      || exit 1

echo "Building docker containers"
docker-compose build                                        || exit 1
