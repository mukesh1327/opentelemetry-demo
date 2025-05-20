# Opentelemetry in Docker

## List of sample applications

Use this README file for endpoints about the sample applications

1. [kafka springboot otel](./00-sample-apps/kafka-springboot-otel/README.md)
2. [springboot-todo-postgresdb](./00-sample-apps/springboot-todo-postgresdb/README.md)

## Steps to run opentelemtey in docker

packages for sample are [here](./01-opentelemetry-docker/sample-application/)
java agent for opentelemetry is [here](./01-opentelemetry-docker/sample-application/javaagent.jar)

This agent can be downloaded from [here](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases)

```bash
docker-compose -f 01-opentelemetry-docker/docker-compose.yaml up -d
```

This {SAMPLE_APP_NAME} can be the jar files placed in [sample-applications](./01-opentelemetry-docker/sample-applications/)  
Currently available examples

- Kafka springboot application [kafka-springboot-app.jar](./01-opentelemetry-docker/sample-applications/kafka-springboot-app.jar)

- springboot todo with postgres [springboot-todo-postgres.jar](./01-opentelemetry-docker/sample-applications/springboot-todo-postgres.jar)

___Run the application___  

```bash
java  -javaagent:./01-opentelemetry-docker/otel-java-agents/javaagent-2-15-0.jar -Dotel.logs.exporter=otlp -Dotel.traces.exporter=otlp -Dotel.metrics.exporter=otlp -Dotel.exporter.otlp.endpoint=http://localhost:4318 -Dotel.service.name=springboot-kafka-demo  -jar ./01-opentelemetry-docker/sample-applications/${SAMPLE_APP_NAME}.jar
```

___Collect telemetry data from kafka consumer in JSON format___
```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmtraces --from-beginning > .\sample-json-files\apmtraces-1.json
```

```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmlogs --from-beginning > .\sample-json-files\apmlogs-1.json
```

```bash
kafka-console-consumer.bat --bootstrap-server localhost:29092 --topic apmmetrics --from-beginning > .\sample-json-files\apmmetrics-1.json
```
