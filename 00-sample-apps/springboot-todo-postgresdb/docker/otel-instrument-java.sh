#!/bin/bash

ls -la /app/

java -javaagent:javaagent-otel-${OTEL_JAVA_INSTRUMENT_JAR_VER}.jar \
     -Dotel.service.name=${SAMPLE_APP_NAME} \
     -Dotel.exporter.otlp.endpoint=${OTEL_URL} \
     -Dotel.traces.exporter=${OTLP_PROTOCOL} \
     -Dotel.metrics.exporter=${OTLP_PROTOCOL} \
     -Dotel.logs.exporter=${OTLP_PROTOCOL} \
     -Dotel.resource.attributes=service.name=${SAMPLE_APP_NAME} \
     -jar /app/${SAMPLE_APP_NAME}.jar
