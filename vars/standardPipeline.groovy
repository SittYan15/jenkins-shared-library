def call(Map config = [:]) {
    pipeline {
        agent any
        
        environment {
            // Using the info you provided earlier
            DOCKER_HUB_USER = "sittyan"
            IMAGE_NAME = "${config.imageName}"
        }

        stages {
            stage('Build & Test') {
                steps {
                    echo "Building ${config.projectName}..."
                    // In a real demo, you'd run 'npm install' here
                    sh 'echo "Running npm install..."'
                }
            }

            stage('Containerize') {
                steps {
                    echo "Creating Docker Image: ${DOCKER_HUB_USER}/${IMAGE_NAME}"
                    // This command builds the image locally on your laptop
                    sh "docker build -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest ."
                }
            }
        }
    }
}