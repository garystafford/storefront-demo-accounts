#!/usr/bin/env bash

# Usage (from root of project): sh ./Docker/docker-build-push.sh

./gradlew clean build
docker build -f Docker/Dockerfile --no-cache -t garystafford/storefront-accounts:gke-2.2.0 .
docker push garystafford/storefront-accounts:gke-2.2.0

# docker run --name storefront-accounts -d garystafford/storefront-accounts:gke-2.2.0
