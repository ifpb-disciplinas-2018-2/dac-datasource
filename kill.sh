mvn clean
docker stop app
docker rm app
docker rmi ricardojob/aula:2
docker stop banco
docker rm banco
docker rmi ricardojob/banco