pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "DOCKERHUB_USERNAME/login-app:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/YOUR_REPO/login-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    docker push $DOCKER_IMAGE
                    '''
                }
            }
        }

        stage('Deploy to K8s') {
            steps {
                sh '''
                kubectl apply -f k8s/
                '''
            }
        }

        stage('Print URL') {
            steps {
                sh '''
                NODE_IP=$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[0].address}')
                echo "Application URL:"
                echo "http://$NODE_IP:30007"
                '''
            }
        }
    }
}

