FROM nginx:latest

# The context of this build should be in the parent directory

ADD apolodoro-nginx/default.conf /etc/nginx/conf.d/default.conf
COPY ./apolodoro-react-frontend/build /etc/nginx/html

CMD /bin/bash -c "nginx -g 'daemon off;'"
