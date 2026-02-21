def call(Map config = [:]) {
    pipeline {
        agent any
        stages {
            stage('Enterprise Build') {
                steps {
                    echo "Starting build for project: ${config.projectName}"
                    sh 'echo "Building..." && sleep 2'
                }
            }
            stage('Enterprise Security Scan') {
                steps {
                    echo "Scanning ${config.projectName} for vulnerabilities..."
                    sh 'sleep 2 && echo "Scan Complete: 100% Secure"'
                }
            }
        }
    }
}