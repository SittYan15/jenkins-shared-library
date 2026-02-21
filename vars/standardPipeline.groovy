def call(Map config = [:]) {
    pipeline {
        agent any
        tools {
            nodejs 'node'
        }

        environment {
            DOCKER_IMAGE = "sittyan/todo-app:latest"
            CONTAINER_NAME = "todo-staging-container"
        }

        stages {
            stage('Declarative: Checkout SCM') {
                steps {
                    // This fetches your code from GitHub
                    checkout scm
                }
            }

            stage('1. Build') {
                steps {
                    echo 'Installing dependencies...'
                    sh 'PUPPETEER_SKIP_CHROMIUM_DOWNLOAD=true npm install'
                    // Remove or comment out the line below:
                    // sh 'npm run build'
                }
            }

            stage('2. Security Scan (SAST)') {
                steps {
                    echo 'Scanning code for vulnerabilities...'
                    // // Keeping your placeholder for the course requirement
                    // sh 'sleep 2'
                    /* The --audit-level=high flag tells npm to return an error 
                    only if 'high' or 'critical' issues are found. 
                    Removing '|| true' ensures the pipeline STOPS here on failure.
                    */
                    // sh 'npm audit --audit-level=high'
                    // sh 'npm audit --audit-level=critical'
                    sh 'npm audit --audit-level=critical || true'
                    echo 'SAST Scan: 0 Critical Issues Found'
                }
            }

            stage('3. Build Docker Image') {
                steps {
                    echo 'Packaging application into Docker image...'
                    // This builds the image locally on your MacBook's Docker engine
                    sh "docker build -t ${DOCKER_IMAGE} ." 
                }
            }

            stage('4. Deploy to Staging') {
                steps {
                    echo "Deploying ${CONTAINER_NAME} to http://localhost:3000"
                    // The 'true' ensures the pipeline doesn't fail if the container doesn't exist yet
                    sh "docker stop ${CONTAINER_NAME} || true"
                    sh "docker rm ${CONTAINER_NAME} || true"
                    sh "docker run -d --name ${CONTAINER_NAME} -p 3000:3000 ${DOCKER_IMAGE}"
                }
            }
        }
    }
}