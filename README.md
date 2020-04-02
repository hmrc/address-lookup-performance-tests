
Address lookup performance tests
===================

Performance test repository for address-lookup service

This repository contains tests for both the backend and frontend service for address-lookup. The default journey runs the backend service.
    
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