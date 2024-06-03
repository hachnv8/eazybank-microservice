[//]: # (dockker mysql build)
docker run -p 3307:3306 --name accountdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountdb -d mysql
docker run -p 3308:3306 --name carddb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=carddb -d mysql
docker run -p 3309:3306 --name loandb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loandb -d mysql