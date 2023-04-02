pipeline {
    agent any
    stages {
        stage('config workspace') {
            steps {
                echo 'config workspace'

                //sh 'rm $HOME/.m2/settings.xml'
                sh 'cp ./backend/assets/settings.xml $HOME/.m2/settings.xml'
                sh 'cat  $HOME/.m2/settings.xml'

                sh '''
                    java -version
                    javac -version
                    mvn -v
                    echo $JAVA_HOME
                '''
                //env.JAVA_HOME="${tool 'jdk1.8.0_111'}"
                //env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
            }
        }
        stage('Build backend') {
            when { branch "Develop" }
            steps {
                dir("./backend") {
                    echo 'Building...'
                    sh 'mvn clean validate'
                }
            }
        }
        stage('Test backend') {
            when { branch "Develop" }
            steps {
                dir("./backend") {
                    echo 'Building...'
                    sh 'mvn test'
                }
            }
        }
        stage('Build cli') {
            when { branch "Develop" }
            steps {
                dir("./cli") {
                    echo 'Building...'
                    sh 'mvn clean validate'
                }
            }
        }
        stage('Test cli') {
            when { branch "Develop" }
            steps {
                dir("./cli") {
                    echo 'Building...'
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy jar') {
            when { branch "devops" }
            steps {
                dir("./backend") {
                    sh 'mvn deploy -U -e'
                }
                //sh 'curl -u admin:zEBf7mD2aCHA8XG4 -O http://vmpx08.polytech.unice.fr:8002/artifactory/libs-snapshot-local/fr/polytech/isa-devops-22-23-team-h-23/1.0-SNAPSHOT/isa-devops-22-23-team-h-23-1.0-20230330.071841-1.jar'
                //sh 'ls -l'
            }
        }
    }

}