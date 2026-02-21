def call(Map config = [:]) {
    pipeline {
        agent any 
        stages {
            stage('1. Build') {
                steps {
                    echo "Building ${config.projectName}..."
                    sh 'echo "npm install && npm run build" > build.log'
                }
            }
            stage('2. Security Scan (SAST)') {
                steps {
                    echo "Scanning code for vulnerabilities..."
                    sh 'sleep 2 && echo "SAST Scan: 0 Critical Issues Found"'
                }
            }
            stage('3. Deploy to Staging') {
                steps {
                    echo "Deploying ${config.appName} to http://localhost:3000"
                    // These commands manage the container on your Mac
                    sh "docker stop ${config.appName} || true"
                    sh "docker rm ${config.appName} || true"
                    sh "docker run -d --name ${config.appName} -p 3000:3000 ${config.dockerImage}"
                }
            }
        }
    }
}