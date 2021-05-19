FROM garystafford/storefront-base:3.0.0
COPY /build/libs/*.jar /storefront/app.jar
WORKDIR /storefront
CMD ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=minikube", "app.jar"]
