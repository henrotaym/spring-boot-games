FROM ubuntu:24.10

ARG TARGETPLATFORM
ARG BUILDPLATFORM
ARG JDK_VERSION=23.0.1
ARG MAVEN_VERSION=3.9.9
ARG UID=1000
ARG GID=1000
ARG APP_PORT=8080

# Download java binaries
RUN arch=$(echo ${TARGETPLATFORM} | sed 's/.*\///') && \
    if [ $arch = "amd64" ]; \
        then jdkArch="x64"; \
        else jdkArch="aarch64"; \
    fi && \
    apt-get update && \
    apt-get install -y wget && \
    wget --output-document=/tmp/jdk.tar.gz https://download.java.net/java/GA/jdk${JDK_VERSION}/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-${JDK_VERSION}_linux-${jdkArch}_bin.tar.gz && \
    tar --extract --verbose --file=/tmp/jdk.tar.gz --directory=/usr/local && \
    rm /tmp/jdk.tar.gz

# Make java binaries globally available
ENV JAVA_HOME="/usr/local/jdk-${JDK_VERSION}"
ENV PATH="$JAVA_HOME/bin:$PATH"

# Download maven
RUN wget --output-document=/tmp/maven.tar.gz https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz &&\
    tar --extract --verbose --file=/tmp/maven.tar.gz --directory=/usr/local && \
    rm /tmp/maven.tar.gz

# Make maven binaries globally available
ENV MAVEN_HOME="/usr/local/apache-maven-${MAVEN_VERSION}"
ENV MAVEN_OPTS="-Dmaven.repo.local=/opt/apps/app/.m2"
ENV PATH="$MAVEN_HOME/bin:${PATH}"

# Install git and gitmoji (optional)
RUN apt-get update && \
    apt-get install -y git nodejs npm && \
    npm install -g gitmoji-cli

# Create user matching host (permissions issue)
RUN if ! getent group ${GID} > /dev/null; then \
    groupadd -g ${GID} app; fi && \
    if ! getent passwd ${UID} > /dev/null; then \
    useradd -u ${UID} -g ${GID} -m -s /bin/bash app; fi

# Use host user
USER ${UID}:${GID}

# Set working directory
WORKDIR /opt/apps/app

# Copy sourcecode
COPY --chown=${UID}:${GID} . .

# Make devops scripts executable
RUN chmod +x ./devops/*.sh

# Expose tomcat server port
EXPOSE ${APP_PORT}
