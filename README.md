
./gradlew build && java -jar build/libs/demo-0.0.1.jar

sudo docker build --build-arg JAR_FILE=build/libs/\*.jar -t currencymonitor .

sudo docker run -d -p 8080:8080 -t currencymonitor:latest

http://localhost:8080/

http://localhost:8080/currency/amd

sudo docker ps

CONTAINER ID        IMAGE                    COMMAND               CREATED             STATUS              PORTS                    NAMES
fb8d19a7a26a        currencymonitor:latest   "java -jar app.jar"   31 minutes ago      Up 31 minutes       0.0.0.0:8080->8080/tcp   determined_margulis

sudo docker stop fb8d19a7a26a

