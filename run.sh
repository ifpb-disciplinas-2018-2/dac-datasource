mvn clean package
docker build -t ricardojob/aula-1 .
docker run -p 8081:8080 -d --name app ricardojob/aula-1