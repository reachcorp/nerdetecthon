FROM openjdk:8-jre-alpine

ENV NERDETECTHON_SLEEP=0 \
    JAVA_OPTS=""

# Add a user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh insight
WORKDIR /home/insight

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown insight:insight entrypoint.sh
USER insight

ADD NERdetecthon*.jar app.jar

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080

