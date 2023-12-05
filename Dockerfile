# Starting with this Linux server.
FROM maven:3-eclipse-temurin-21

## Building the application
# Create a directory called /app
# and go into the directory, as if we are doing -> cd /app -> these are Docker directives
WORKDIR /app

# Everything we do after this is in /app
# Copy the files you need
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
# Copy the directories you need
COPY .mvn .mvn
COPY src src

# Build the application
RUN mvn package -Dmaven.test.skip=true


## Run the application
# Define an environment variable (to give the user flexibility)
ENV PORT=3000

# Tell Docker what port it will need / Expose the port
# This references the environment variable above
EXPOSE ${PORT}

# Run the program
# Jar file is only available after you compile
# Must follow the package name (Day17Lecture)
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day17Lecture-0.0.1-SNAPSHOT.jar