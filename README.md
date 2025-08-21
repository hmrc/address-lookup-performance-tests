
Address lookup performance tests
===================

Performance test repository for address-lookup service

This repository contains tests for both the backend and frontend service for address-lookup. The default journey runs the backend service.

## Running locally

### Prerequisites

- Docker installed (for starting MongoDB)
- Service Manager 2 [sm2](https://github.com/hmrc/sm2) installed (for starting the address lookup services)

### Starting the services

If you don't have Mongo running locally, then startup via Docker container as follows:

```bash
  docker run --restart unless-stopped --name mongodb -p 27017:27017 -d percona/percona-server-mongodb:7.0 --replSet rs0
  docker exec -it mongodb mongosh --eval "rs.initiate();"
````

Start dependent microservices using the following shell script:
```shell
  ./start_services.sh
```

### Smoke test

There are two separate scripts for running the smoke tests, one for the backend service and one for the frontend service.

#### API Performance Tests
To run the smoke tests on the backend, use the following script:
```shell
    ./run-local.sh
```
#### Frontend Performance Tests
To run the smoke tests on the frontend, use the following script:
```shell
    ./run-frontend-local.sh
```

### Checking test failures

Some additional information is being logged is Gatling detects a KO response, this information will be displayed in the simulation.log file held with the Gatling report (`*/adresslookupsimulation-<date>/simulation.log`).
