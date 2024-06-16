[//]: # (dockker mysql build)
docker run -p 3307:3306 --name accountdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountdb -d mysql
docker run -p 3308:3306 --name carddb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=carddb -d mysql
docker run -p 3309:3306 --name loandb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loandb -d mysql

[//]: # (redis)
docker run -p 6379:6379 --name eazyredis -d redis

[//]: # (apache benchmark)
ab -n 10 -c 2 -v 3 http://localhost:8072/eazybank/card-service/api/contact-info

[//]: # (keycloak)
docker run -p 7080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.0 start-dev

[//]: # (rabbitmq)
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management