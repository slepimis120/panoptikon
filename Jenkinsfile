pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Set Permissions'){
            steps{
                script{
                    sh 'chmod +x ./mvnw'
                }
            }
        }
        stage('Build') {
            steps {
                dir('employee-manager') {
                    sh './mvnw clean install'
                }
            }
        }
        stage('Test') {
            steps {
                dir('employee-manager') {
                    sh './mvnw test'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
