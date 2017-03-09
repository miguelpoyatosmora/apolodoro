#!/bin/bash

cd apolodoro-react-frontend
npm run build
cd ..

mvn clean package -T 8

docker-compose build
