
Address lookup performance tests
===================

Performance test repository for address-lookup service

This repository contains tests for both the backend and frontend service for address-lookup. The default journey runs the backend service.

## Running on a build Agent
    
### Smoke test

To run one journey with one user
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

### Run the performance test (Default, backend service)
```
sbt gatling:test
```

### Run the performance test for frontend service
```
sbt -DjourneysToRun.0=address-lookup-frontend gatling:test
```

## Running locally

Before you run any tests locally you will need to start up address lookup using service manager (This does require a local mongodb instance).

To run mongodb locally with docker:

```
docker run --rm --name mongodb -p 27017-27019:27017-27019 mongo:4
```
To start the services up with service manager:

```
sm2 --start ADDRESS_LOOKUP_SERVICES -r --appendArgs '{  
        "ADDRESS_LOOKUP":[                                                                           
            "-J-Dmicroservice.services.access-control.allow-list.0=address-lookup-frontend"
        ],
        "ADDRESS_LOOKUP_FRONTEND":[
            "-J-Dapplication.router=testOnlyDoNotUseInAppConf.Routes"                                    
        ]
    }'    
```

A couple of scripts have been created to make it easier to run the tests locally they are as follows:

#### API Performance Tests

```./run-local.sh```

#### Frontend Performance Tests

```./run-frontend-local.sh```

### Checking test failures

Some additional information is being logged is Gatling detects a KO response, this information will be displayed in the simulation.log file held with the Gatling report (`*/adresslookupsimulation-<date>/simulation.log`).
