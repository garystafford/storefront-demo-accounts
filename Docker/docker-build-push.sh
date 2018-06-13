#!/usr/bin/env bash

./gradlew clean build
docker build -f Docker/Dockerfile --no-cache -t garystafford/storefront-accounts:latest .
docker push garystafford/storefront-accounts:latest

# docker run --name storefront-accounts -d garystafford/storefront-accounts:latest