#!/usr/bin/env bash

SMOKE=${1:-true}
LOCAL=${2:-true}

sbt gatling:test \
  -DrunLocal=${LOCAL} \
  -Dperftest.runSmokeTest=${SMOKE} \
  -Djourneys.address-lookup-api.feeder=data/local/postcodes.csv \
  -Djourneys.address-lookup-api-fuzzy.feeder=data/local/fuzzy.csv \
  -Djourneys.address-lookup-api-international.feeder=data/local/international.csv \
  -Djourneys.address-lookup-api-uprn.feeder=data/local/uprn.csv

