# Java Quarkus todo world - Noventiq value point

__A demo todo world java quarkus application__

___Running on port :___ 8080

___Endpoints :___   
    /

___Environment variables :___   
APP_TITLE=string



## How to run in local (Use any one)

<u>**_Run as JVM (Java JDK)_**</u>  
> mvn quakus:dev

<u>**_Run as native (Requires podman or docker and GraalVM runtimes)_**</u>  
> mvn quakus:dev -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=podman

## Run as container (Use any one)

<u>**_Run as JVM_**</u>   

> mvn package

> podman build -t quarkus-jvm-todo:v1.0 -f ./src/main/docker/Dockerfile.jvm .

> podman run -d -p 8080:8080 --name quarkus-jvm-todo quarkus-jvm-todo:v1.0

<u>**_Run as Native_** </u>

> mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=podman

> podman build -t quarkus-native-todo:v1.0 -f ./src/main/docker/Dockerfile.native .

> podman run -d -p 8080:8080 --name quarkus-native-todo quarkus-native-todo:v1.0

<u>**_Run as Native-micro_**</u>

> mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=podman

> podman build -t quarkus-nativemicro-todo:v1.0 -f ./src/main/docker/Dockerfile.native-micro .

> podman run -d -p 8080:8080 --name quarkus-nativemicro-todo quarkus-nativemicro-todo:v1.0

## Deploy in k8s-ocp (Use any one)

<u>**_Run as JVM_**</u>  

> oc apply -f ./k8s-manifests/jvm -n demo-namespace

<u>**_Run as Native_** </u>

> oc apply -f ./k8s-manifests/native -n demo-namespace

<u>**_Run as Native-micro_**</u>

> oc apply -f ./k8s-manifests/native-micro -n demo-namespace


Endpoints to try 

POST /api/tasks/create (to create a task)

    {
        "name": "Test Task",
        "description": "This is a sample task",
        "status": "PENDING",
        "dueDate": "2025-05-19T00:00:00Z"
    }


GET /api/tasks/read (to get all tasks)


GET /api/tasks/get/{taskId} (to get a task by ID)


PUT /api/tasks/update/{taskId} (to update a task)

    {
    "title": "Updated Task",
    "status": "DONE",
    "description": "Updated description"
    }

DELETE /api/tasks/delete/{taskId} (to delete a task)
