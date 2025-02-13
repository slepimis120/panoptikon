FROM jenkins/jenkins:lts-jdk21

EXPOSE 8080 50000

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

COPY infra/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

COPY infra/jenkins.yaml /var/jenkins_home/jenkins.yaml
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/jenkins.yaml

COPY infra/github-pipeline.groovy /var/jenkins_home/github-pipeline.groovy

RUN mkdir -p /var/jenkins_home/.ssh && \
    chmod 700 /var/jenkins_home/.ssh

RUN ssh-keyscan github.com >> /var/jenkins_home/.ssh/known_hosts

CMD ["java", "-jar", "/usr/share/jenkins/jenkins.war"]