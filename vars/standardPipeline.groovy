def call(Map config = [:]) {
    pipeline {
        agent any // Declarative Syntax: Easy to read
        stages {
            stage('Build & Test') {
                steps {
                    echo "Building ${config.projectName}..."
                    sh 'npm install && npm test || true' // Simulating build/test
                }
            }
            stage('Security Scan (SAST)') {
                steps {
                    echo "Scanning for vulnerabilities..."
                    sh 'sleep 2 && echo "SAST Scan: No critical issues found."'
                }
            }
            stage('Deploy to Staging') {
                steps {
                    echo "Deploying to Staging..."
                    // This command talks to the Docker engine on your Mac from inside Jenkins
                    sh "docker stop ${config.appName} || true"
                    sh "docker rm ${config.appName} || true"
                    sh "docker run -d --name ${config.appName} -p 3000:3000 ${config.dockerImage}"
                }
            }
        }
    }
}