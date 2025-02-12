FROM jenkins/jenkins:lts-jdk21

EXPOSE 8080 50000

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

COPY jenkins.yaml /var/jenkins_home/jenkins.yaml

ENV CASC_JENKINS_CONFIG=/var/jenkins_home/jenkins.yaml

CMD ["java", "-jar", "/usr/share/jenkins/jenkins.war"]
