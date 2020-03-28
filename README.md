
performance-testing
===================

Performance test repository for address-lookup service
    
### Smoke test

To run one journey with one user
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

### Run the performance test
```
sbt gatling:test
```
