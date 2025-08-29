#!/usr/bin/env bash

sm2 --start ADDRESS_LOOKUP_SERVICES --appendArgs '{
    "ADDRESS_LOOKUP":[
        "-Dmicroservice.services.access-control.allow-list.0=address-lookup-frontend"
    ],
    "ADDRESS_LOOKUP_FRONTEND":[
        "-Dapplication.router=testOnlyDoNotUseInAppConf.Routes"
    ]
}'