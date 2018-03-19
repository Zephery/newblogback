kill -9 $(netstat -nlp | grep :8081 | awk '{print $7}' | awk -F"/" '{ print $1 }')
git pull origin master
mvn clean
mvn install
nohup java -jar target/myblogback.jar &
