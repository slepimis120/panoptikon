FROM jenkins/jenkins:lts-jdk21

EXPOSE 8080 50000

COPY jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml

ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/jenkins.yaml

CMD ["java", "-Djenkins.install.runSetupWizard=false", "-jar", "/usr/share/jenkins/jenkins.war"]
