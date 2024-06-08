[//]: # (dockker mysql build)
docker run -p 3307:3306 --name accountdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountdb -d mysql
docker run -p 3308:3306 --name carddb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=carddb -d mysql
docker run -p 3309:3306 --name loandb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loandb -d mysql

[//]: # (redis)
docker run -p 6379:6379 --name eazyredis -d redis

[//]: # (apache benchmark)
ab -n 10 -c 2 -v 3 http://localhost:8072/eazybank/card-service/api/contact-info