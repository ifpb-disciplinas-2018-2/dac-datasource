#criando imagem dp postgreSQL
docker build -t ricardojob/banco ./postgres
docker run -p 5433:5432 -d --name banco ricardojob/banco

#criando imagem da aplicação
mvn clean package
docker build -t ricardojob/aula:2 .
docker run -p 8081:8080 -d --name app --link banco:host-banco ricardojob/aula:2
