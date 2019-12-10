FROM openjdk:8-jre-alpine

ENV APP_NAME idwall-reddit-crawler
ENV APP_HOME /usr/bin/$APP_NAME
ENV USER_NAME idwall

ENV LANG=UTF-8 \
    LANGUAGE=UTF-8 \
    LC_CTYPE=UTF-8 \
    LC_ALL=UTF-8

ARG JAR_FILE=idwall-reddit-crawler-1.0-SNAPSHOT.jar
ARG LOG_DIR=$APP_HOME/logs

ENV APPLICATION $APP_HOME/$JAR_FILE

# Copy application
RUN mkdir -p $APP_HOME
COPY target/$JAR_FILE $APP_HOME/$JAR_FILE
RUN chmod a+rwx $APPLICATION

# Create logs
RUN mkdir -p $LOG_DIR
RUN chmod a+rwx $LOG_DIR

CMD ["sh", "-c", "java -jar ${APPLICATION}"]

