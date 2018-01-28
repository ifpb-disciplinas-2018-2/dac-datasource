mvn clean
docker stop app
docker rm app
docker rmi ricardojob/datasource
docker stop banco
docker rm banco
docker rmi ricardojob/banco