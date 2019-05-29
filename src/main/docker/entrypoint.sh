#!/bin/sh

echo "The application will start in ${NERDETECTHON_SLEEP}s..." && sleep ${NERDETECTHON_SLEEP}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.jar" "$@"
