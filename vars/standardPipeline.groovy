def call(Map config = [:]) {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo "Building ${config.projectName}..."
                    sh 'echo "npm install && npm build" > build-log.txt'
                }
            }
            stage('Security Scan (SAST)') {
                steps {
                    echo "Checking for vulnerabilities..."
                    // Simulating a security tool like Snyk or SonarQube
                    sh 'sleep 2 && echo "Scan Complete: 0 Vulnerabilities Found"'
                }
            }
            stage('Deploy to Staging') {
                steps {
                    echo "Deploying to Staging Server: ${config.stagingUrl}"
                    sh 'echo "Deployed successfully!"'
                }
            }
        }
    }
}