pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "surysaho/login-app:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
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
                    credentialsId: 'suryasaho-docker',
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

        stage('Create a Namespace') {
            steps {
                sh '''
                kubectl apply -f k8s/namespace.yaml
                '''
                sh '''
                kubectl apply -f k8s/
                '''
            }
        }

        stage('Pod status') {
            steps {
                sh '''
                sleep 10 
                '''
                sh '''
                kubectl get po -n surya 
                '''
            }
        }

        stage('Print URL') {
            steps {
                sh '''
                NODE_IP=$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[0].address}')
                echo "Application URL:"
                echo "http://$NODE_IP:30009"
                '''
            }
        }
    }
}

