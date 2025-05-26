# Opentelemetry in Docker

## List of sample applications

Use this README file for endpoints about the sample applications

1. [springboot-kafka](./00-sample-apps/kafka-springboot-otel/README.md)
2. [springboot-todo-postgresdb](./00-sample-apps/springboot-todo-postgresdb/README.md)

## Steps to run opentelemtey in docker/podman

Packages for sample are [here](./01-opentelemetry-container/sample-application/)  
java agent for opentelemetry is [here](./01-opentelemetry-container/sample-application/javaagent.jar)

This agent can be downloaded from [here](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases)

```shell script
docker-compose -f 01-opentelemetry-container/docker-compose.yaml up -d
```

Services that starts up in docker-compose  

1. PostgresDB
2. Kafka and Zookeeper + kafdrop (kafka console)
3. Jaeger
4. Prometheus
5. Grafana
6. OpenTelemetry

### Java apps

For instrumenting java apps.  
Download the opentelemetry javaagent jar from [opentelemetry-java-instrumentation GitHub releases](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases) page

```shell script
# Specify the required version of java otel agent
export OTEL_JAVA_INSTRUMENT_JAR_VER=v2.16.0

# Download the java agent
wget -p 01-opentelemetry-container/otel-agents/javaagent-${OTEL_JAVA_INSTRUMENT_JAR_VER}.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/${OTEL_JAVA_INSTRUMENT_JAR_VER}/opentelemetry-javaagent.jar
```

___Run the application with otel agents___  

{SAMPLE_APP_NAME} is the project name (jar file name) placed in [sample-applications](./01-opentelemetry-container/sample-applications/)  

Available examples to run  

To run apps in local

```shell script
## A demo springboot application with kafka
export SAMPLE_APP_NAME=springboot-todo-postgresdb

# Package the java as jar
mvn -f 00-sample-apps/${SAMPLE_APP_NAME}/pom.xml package -DskipTests

# Change the mvn package jar file name
mv 00-sample-apps/${SAMPLE_APP_NAME}/target/${SAMPLE_APP_NAME}*.jar 00-sample-apps/${SAMPLE_APP_NAME}/target/${SAMPLE_APP_NAME}.jar

# Start the application with jar file
java  -javaagent:./01-opentelemetry-container/otel-agents/javaagent-${OTEL_JAVA_INSTRUMENT_JAR_VER}.jar -Dotel.logs.exporter=otlp -Dotel.traces.exporter=otlp -Dotel.metrics.exporter=otlp -Dotel.exporter.otlp.endpoint=http://localhost:4318 -Dotel.service.name=${SAMPLE_APP_NAME} -jar ./00-sample-apps/${SAMPLE_APP_NAME}/target/${SAMPLE_APP_NAME}.jar
```

___Collect telemetry data from kafka consumer in JSON format___

```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmtraces --from-beginning > ./sample-json-files/apmtraces-1.json
```

```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmlogs --from-beginning > ./sample-json-files/apmlogs-1.json
```

```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmmetrics --from-beginning > ./sample-json-files/apmmetrics-1.json
```
