###Собрать проект.
###Из корня проетка запустить:
./gradlew build && java -jar build/libs/demo-0.0.1.jar

###Упаковка приложения в образ:
sudo docker build --build-arg JAR_FILE=build/libs/\*.jar -t currencymonitor .

###запуск контейнера:
sudo docker run -d -p 8080:8080 -t currencymonitor:latest

###Приложение доступно по адресу, выгружает список кодов валют:
http://localhost:8080/
### обращается к сервису курсов валют, и отдает gif в ответ: если курс по отношению к заданной валюте(в файле настройки)  за сегодня стал выше вчерашнего
http://localhost:8080/currency/{cur}

###Просмотр запущеных контейнеров в докере
sudo docker ps

CONTAINER ID        IMAGE                    COMMAND               CREATED             STATUS              PORTS                    NAMES
fb8d19a7a26a        currencymonitor:latest   "java -jar app.jar"   31 minutes ago      Up 31 minutes       0.0.0.0:8080->8080/tcp   determined_margulis
### остановка контейнера:
sudo docker stop fb8d19a7a26a

