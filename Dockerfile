FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY . .
EXPOSE 8001
ENTRYPOINT ["bash", "./scripts/init_server.sh"]
