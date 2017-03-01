FROM library/elasticsearch:latest

EXPOSE 9200

#RUN mkdir /etc/apolodoro
#COPY ./target/apolodoro-web-0.1.0.jar /etc/apolodoro

RUN mkdir /tmp/apolodoro
COPY . /tmp/apolodoro
RUN chmod +x /tmp/apolodoro/entrypoint.sh

ENTRYPOINT [ "/tmp/apolodoro/entrypoint.sh" ]
CMD [ "elasticsearch" ]


