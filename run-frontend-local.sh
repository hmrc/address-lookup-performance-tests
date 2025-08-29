#!/usr/bin/env bash

SMOKE=${1:-true}
LOCAL=${2:-true}

sbt gatling:test \
  -DrunLocal=${LOCAL} \
  -Dperftest.runSmokeTest=${SMOKE} \
  -DjourneysToRun.0=address-lookup-frontend \
  -Djourneys.address-lookup-frontend.feeder=data/local/postcodes.csv
