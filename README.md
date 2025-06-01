# Opentelemetry in Docker

## Run the observability tools

### List of observability apps running in docker compose file

1. PostgresDB
2. Kafka and Zookeeper + kafdrop (kafka console)
3. Jaeger (or) Grafana tempo
4. Prometheus - [config file](./01-opentelemetry-container/prometheus-container-configs/prometheus.yml)
5. Grafana
6. OpenTelemetry - [config file](./01-opentelemetry-container/otel-container-configs/otelcol-config.yaml)

```shell script
# Create a common network for all applications
podman network create otel-demo
```

```shell script
# Run the observability tools in containers
podman compose -f 01-opentelemetry-container/docker-compose.yaml up -d
```

## Run the sample applications


### List of sample applications

Some apps requires database (Postgres is used for demo apps)

Run the postgres with compose file

```shell script
podman compose -f 00-sample-apps/databases/docker-compose.yaml up -d
```

- Java based apps - Auto Instrumentation

1. [Spring boot Kafka app](./00-sample-apps/springboot-kafka/README.md) Auto-instrumented with java agent running in docker-compose

2. [Spring Boot Todo Postgres](./00-sample-apps/springboot-todo-postgresdb/README.md) Auto-instrumented with java agent running in docker-compose

Download the opentelemetry java agent jar from [opentelemetry-java-instrumentation GitHub releases](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases) page using following commands

If application requires database, setup the database using following

```shell script
podman compose -f 00-sample-apps/databases/docker-compose.yaml up -d
```

```shell script
# Specify the required version of java otel agent
export OTEL_JAVA_INSTRUMENT_JAR_VER=v2.16.0
```

```shell script
# Provide the sample java application name 
export SAMPLE_APP_NAME=folder_name_in_00-sample-apps
```

```shell script
# Download the java agent - For java based applications
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/${OTEL_JAVA_INSTRUMENT_JAR_VER}/opentelemetry-javaagent.jar 
```

```shell script
mkdir -p 00-sample-apps/${SAMPLE_APP_NAME}/otel-agents/
```

```shell script
# Move the downloaded shell script to the sample java application
mv opentelemetry-javaagent.jar 00-sample-apps/${SAMPLE_APP_NAME}/otel-agents/javaagent-otel-${OTEL_JAVA_INSTRUMENT_JAR_VER}.jar
```

```shell script
# Move to the directory where application is present
cd 00-sample-apps/${SAMPLE_APP_NAME}
```

```shell script
# Package the jar file (Verify the installed java version and then run)
./mvnw clean package -DskipTests
```

```shell script
# Build image and run container with docker compose
podman compose -f ./docker/docker-compose.yml up -d
```

## Collect telemetry data from kafka consumer in JSON format

An option step: Since data is already visualized in Grafana dashboard.

Collecting the telemetry data in JSON format is for reference.

```shell script
# Collect traces of the applications
podman exec confluent-kafka_broker /bin/kafka-console-consumer --bootstrap-server localhost:29092 --topic traces --from-beginning > ./sample-json-files/apptraces.json
```

```shell script
# Collect logs of the applications
podman exec confluent-kafka_broker /bin/kafka-console-consumer --bootstrap-server localhost:29092 --topic logs --from-beginning > ./sample-json-files/apmlogs.json
```

```shell script
# collect metrics of the applications
podman exec confluent-kafka_broker /bin/kafka-console-consumer --bootstrap-server localhost:29092 --topic metrics --from-beginning > ./sample-json-files/apmmetrics.json
```
