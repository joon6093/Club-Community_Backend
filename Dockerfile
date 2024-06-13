FROM eclipse-temurin:17 as builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew && ./gradlew bootJar

FROM eclipse-temurin:17-jre as runtime

RUN addgroup --system --gid 1000 worker && \
    adduser --system --uid 1000 --ingroup worker --disabled-password worker

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

RUN mkdir -p /app/images /app/videos /app/files && \
    chown -R worker:worker /app/images /app/videos /app/files

USER worker:worker

ENV PROFILE ${PROFILE}

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app/app.jar"]
