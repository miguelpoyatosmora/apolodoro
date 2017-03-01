#!/bin/bash


echo "Instantiating elasticsearch"

/docker-entrypoint.sh $1 &

java -jar /etc/apolodoro/apolodoro-web-0.1.0.jar &



sleep 15
echo "Copying files to elasticsearch"

function upload {

  fileBasename=$(basename "$1")
  echo "Uploading "$1" to http://localhost:9200/apolodoro/users/"$fileBasename
  curl -s -XPUT localhost:9200/apolodoro/users/$fileBasename -d $1
} 
 
find /tmp/apolodoro/users  -type f | while read file; do upload "$file"; done

