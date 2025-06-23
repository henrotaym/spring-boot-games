ARG UBUNTU_VERSION=24.10
ARG NODE_VERSION=22.15.0

# ------------- BASE STEP -------------
FROM ubuntu:${UBUNTU_VERSION} AS base

ARG TARGETPLATFORM
ARG BUILDPLATFORM
ARG JDK_VERSION=23.0.1
ARG MAVEN_VERSION=3.9.9

# Install dependencies
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates wget curl gnupg && \
    update-ca-certificates && \
    rm -rf /var/lib/apt/lists/*

# Download java binaries
RUN arch=$(echo ${TARGETPLATFORM} | sed 's/.*\///') && \
    if [ $arch = "amd64" ]; \
        then jdkArch="x64"; \
        else jdkArch="aarch64"; \
    fi && \
    wget --output-document=/tmp/jdk.tar.gz https://download.java.net/java/GA/jdk${JDK_VERSION}/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-${JDK_VERSION}_linux-${jdkArch}_bin.tar.gz && \
    tar --extract --verbose --file=/tmp/jdk.tar.gz --directory=/usr/local && \
    rm /tmp/jdk.tar.gz

# Make java binaries globally available
ENV JAVA_HOME="/usr/local/jdk-${JDK_VERSION}"
ENV PATH="$JAVA_HOME/bin:$PATH"

# ------------- MAVEN STEP -------------
FROM base AS maven

# Download maven
RUN wget --output-document=/tmp/maven.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz &&\
    tar --extract --verbose --file=/tmp/maven.tar.gz --directory=/usr/local && \
    rm /tmp/maven.tar.gz

# Make maven binaries globally available
ENV MAVEN_HOME="/usr/local/apache-maven-${MAVEN_VERSION}"
ENV MAVEN_OPTS="-Dmaven.repo.local=./.m2"
ENV PATH="$MAVEN_HOME/bin:${PATH}"

# ------------- NODE STEP -------------
FROM node:${NODE_VERSION}-slim AS node

# ------------- BUILD STEP -------------
FROM maven AS build

# Set working directory
WORKDIR /opt/apps/app

# Copy package dependencies
COPY pom.xml .
COPY src ./src

# Build JAR archive
RUN mvn clean package -DskipTests

# ------------- DEPLOY STEP -------------
FROM base AS deploy

# Install doppler
RUN apt-get update && apt-get install -y apt-transport-https ca-certificates curl gnupg && \
    curl -sLf --retry 3 --tlsv1.2 --proto "=https" 'https://packages.doppler.com/public/cli/gpg.DE2A7741A397C129.key' | gpg --dearmor -o /usr/share/keyrings/doppler-archive-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/doppler-archive-keyring.gpg] https://packages.doppler.com/public/cli/deb/debian any-version main" | tee /etc/apt/sources.list.d/doppler-cli.list && \
    apt-get update && \
    apt-get install doppler

# Create non-root user for security
RUN groupadd -g 1001 spring && \
    useradd -u 1001 -g spring -s /bin/bash -m spring

USER spring:spring

# Set working directory
WORKDIR /opt/apps/app

# Copy JAR archive
COPY --chown=spring:spring --from=build /opt/apps/app/target/*.jar ./app.jar

COPY --chown=spring:spring ./devops/deployment/run.sh ./run.sh
RUN chmod +x ./run.sh

# Expose tomcat server port
EXPOSE 8080

CMD ["./run.sh"]

# ------------- HTTP -------------
FROM deploy AS http

ENV SPRING_ACTIVE_PROFILE=http

# ------------- QUEUE -------------
FROM deploy AS queue

ENV SPRING_ACTIVE_PROFILE=queue

# ------------- SCHEDULER -------------
FROM deploy AS scheduler

ENV SPRING_ACTIVE_PROFILE=scheduler

# ------------- LOCAL -------------
FROM maven AS local

ARG GOOGLE_JAVA_FORMAT_VERSION=1.27.0
ARG USER_ID=1000
ARG GROUP_ID=1000
ARG APP_PORT=8080

# Install git
RUN apt-get update && \
    apt-get install -y git nano && \
    wget --output-document=/etc/bash_completion.d/git-completion.bash \
        https://raw.githubusercontent.com/git/git/refs/heads/master/contrib/completion/git-completion.bash && \
    echo "source /etc/bash_completion.d/git-completion.bash" >> /etc/bash.bashrc

ENV GIT_EDITOR=nano

# Install google java formatter
RUN wget --output-document=/usr/local/lib/google-java-format.jar https://github.com/google/google-java-format/releases/download/v${GOOGLE_JAVA_FORMAT_VERSION}/google-java-format-${GOOGLE_JAVA_FORMAT_VERSION}-all-deps.jar && \
    chmod +x /usr/local/lib/google-java-format.jar

# Install gitmoji & lefthook
COPY --from=node --chown=${USER_ID}:${GROUP_ID} /usr/local/lib /usr/local/lib
COPY --from=node --chown=${USER_ID}:${GROUP_ID} /usr/local/include /usr/local/include
COPY --from=node --chown=${USER_ID}:${GROUP_ID} /usr/local/bin /usr/local/bin

RUN npm install --global gitmoji-cli lefthook git-open

# Create user matching host (permissions issue)
RUN if ! getent group ${GROUP_ID} > /dev/null; then \
    groupadd -g ${GROUP_ID} app; fi && \
    if ! getent passwd ${USER_ID} > /dev/null; then \
    useradd -u ${USER_ID} -g ${GROUP_ID} -m -s /bin/bash app; fi

# Use host user
USER ${USER_ID}:${GROUP_ID}

# Set working directory
WORKDIR /opt/apps/app

# Copy sourcecode
COPY --chown=${USER_ID}:${GROUP_ID} . .

# Make devops scripts executable
RUN chmod +x ./devops/*.sh

# Expose tomcat server port
# EXPOSE ${APP_PORT}

CMD [ "sleep", "infinity" ]

# Default stage to load.
FROM http