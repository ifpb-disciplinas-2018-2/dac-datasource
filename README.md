## Criar o arquivo `Dockerfile` do banco PostgreSQL
```
FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 12345
ENV POSTGRES_DB dac-cliente
COPY create.sql /docker-entrypoint-initdb.d/
COPY insert.sql /docker-entrypoint-initdb.d/
```
## Criar uma imagem do banco PostgreSQL
`docker build -t ricardojob/banco ./postgres`:  
*`-t`: qual a tag que vamos atribuir a essa imagem*  
*`./postgres`: caminho relativo (ou absoluto) para o arquivo Dockerfile*  


## Criar o arquivo `Dockerfile` da aplicação
```
FROM payara/server-full
ENV DOMAIN domain1
ENV LIB /opt/payara41/glassfish/domains/${DOMAIN}/lib/
ENV DEPLOY ${PAYARA_PATH}/glassfish/domains/${DOMAIN}/autodeploy/
ADD  target/datasource/WEB-INF/lib/ ${LIB}
ENTRYPOINT $PAYARA_PATH/bin/asadmin start-domain --verbose ${DOMAIN}
ADD  target/datasource.war  ${DEPLOY}
```

## Criar o arquivo `glassfish-resources.xml`, na pasta `WEB-INF`. Algumas das configurações para criação do Pool de Conexão e recurso JDBC.

```
<resources>
    <jdbc-connection-pool allow-non-component-callers="false" 
                          name="post-gre-sql_exemplo-dac_postgresPool" 
                          wrap-jdbc-objects="false">
        <property name="serverName" value="host-banco"/>
        <property name="portNumber" value="5432"/>
        <property name="databaseName" value="dac-cliente"/>
        <property name="User" value="postgres"/>
        <property name="Password" value="12345"/>
        <property name="driverClass" value="org.postgresql.Driver"/>
    </jdbc-connection-pool>
    <jdbc-resource enabled="true" jndi-name="java:app/jdbc/exemplo" 
                   object-type="user" pool-name="post-gre-sql_exemplo-dac_postgresPool"/>
</resources>
```

## Criar uma imagem da aplicação

`docker build -t ricardojob/datasource .`:  
*`-t`: qual a tag que vamos atribuir a essa imagem*  
*`.`: caminho relativo (ou absoluto) para o arquivo Dockerfile*  

## Executar o container  
`docker run -p 5433:5432 -d --name banco ricardojob/banco` e 
`docker run -p 8080:8080 -p 4848:4848 -d --name app --link banco:host-banco ricardojob/datasource`:   
*`-p`: o bind entre a porta do host local com a porta do container*  
*`-d`: o container seja executar em background*  
*`--link`: o bind entre os containers, para pertimir que o container da aplicação tenha acesso ao container do banco*  
*`--name`: o nome do container*  


## Listar as imagens

`docker image ls`

## Listar os containers

`docker container ls`

## Parar o container

`docker stop <container_id | container_name>`

## Executar comandos no container  
Para executarmos comandos necessitamos de executar o comando `docker exec -it <container_id | container_name> <command>`. 
Por exemplo, para termos acesso ao container do banco que configuramos podemos fazer:

`docker exec -it banco /bin/bash`:  
*`-it`: para termos acesso iterativo ao TTY*  
*`banco`: o nome do container que desejamos seja executar determinado comando*  
*`/bin/bash`: o comando que vamos executar no container*  

Após esses passos, teremos acesso ao terminal do container. Podemos acessar o _database_ que definimos no arquivo `Dockerfile` que configura o banco de dados, neste exemplo `dac-cliente`.

`psql -U postgres dac-cliente`:  
*`-U`: usuário configurado*  
*`dac-cliente`: o _database_ que desejamos acessar* 

Alguns comando úteis no `psql`:  
*`\dt`: lista as tabelas do _database_*    
*`select * from cliente;`: seleciona todos os clientes*  
*`INSERT INTO cliente(nome, cpf) VALUES ('Kiko','123.132.121-31');`: insere um novo cliente*    
*`\q`: sair do _database_*  