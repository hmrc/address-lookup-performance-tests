
Address lookup performance tests
===================

Performance test repository for address-lookup service

This repository contains tests for both the backend and frontend service for address-lookup. The default journey runs the backend service.

## Running Tests

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

### Test Data

The performance tests use "feeder" files to generate test data for the Gatling journeys. There are two sets of feeder files:

- Local data (for the canned/stubbed test data held in `address-search-api`)
  - [data/local/postcodes.csv](src/test/resources/data/local/postcodes.csv) 
  - [data/local/international.csv](src/test/resources/data/local/international.csv) 
  - [data/local/uprn.csv](src/test/resources/data/local/uprn.csv) 
  - [data/local/fuzzy.csv](src/test/resources/data/local/fuzzy.csv) 


- Staging data (for the test data held within the Staging environment)
  - [data/staging/postcodes.csv](src/test/resources/data/staging/postcodes.csv)
  - [data/staging/international.csv](src/test/resources/data/staging/international.csv)
  - [data/staging/uprn.csv](src/test/resources/data/staging/uprn.csv)
  - [data/staging/fuzzy.csv](src/test/resources/data/staging/fuzzy.csv)


### Smoke test

There are two separate scripts for running the smoke tests, one for the backend service and one for the frontend service.

**Note:** It is important to use these scripts locally as they specify separate local journeys which use separate feeder files for the test data.

#### API Performance Tests
To run the smoke tests on the backend, use the following script:
```shell
    ./run-api-local.sh
```
#### Frontend Performance Tests
To run the smoke tests on the frontend, use the following script:
```shell
    ./run-frontend-local.sh
```

### Checking test failures

Some additional information is being logged is Gatling detects a KO response, this information will be displayed in the simulation.log file held with the Gatling report (`*/adresslookupsimulation-<date>/simulation.log`).
