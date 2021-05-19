#!/usr/bin/env bash

# Usage (from root of project): sh ./Docker/docker-build-push.sh

# docker build -f Docker/Dockerfile_base --no-cache -t garystafford/storefront-base:3.0.0 .

./gradlew clean build
docker build -f Docker/Dockerfile --no-cache -t garystafford/storefront-accounts:3.0.0-minikube .
docker push garystafford/storefront-accounts:3.0.0-minikube

# docker run --name storefront-accounts -d garystafford/storefront-accounts:3.0.0
