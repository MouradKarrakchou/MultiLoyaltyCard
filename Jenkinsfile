pipeline {
    agent any
    tools {
        maven 'maven-3.9.0' 
    }
    stages {
        stage('Build') {
            steps {
                ws('/backend'){
                    echo 'Building.. Iraana II'
                    sh 'ls -l'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}