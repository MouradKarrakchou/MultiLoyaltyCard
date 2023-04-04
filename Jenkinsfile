def directories = [
        "backend",
        "cli"
]

pipeline {
    agent any

    environment {
		DOCKERHUB_CREDENTIALS=credentials('dockerhub-cred')
		containerWork = true
		endToEndAvailable = true
        skipSteps = false
	}

    stages {
        stage('config workspace') {
            steps {
                echo 'config workspace'

                // Check if the commit is from maven-release-plugin
                script {
                    def commitMessage = sh(returnStdout: true, script: 'git log -1 --pretty=%B').trim()
                    if (commitMessage.contains('[maven-release-plugin]')) {
                        echo 'Exiting building'
                        // Sortir de la pipeline et ne pas executer les aures stage avec SUCCESS
                        skipSteps = true
                        return
                    }
                    try {
                        sh 'docker rm bank db server cli'
                    } catch (Exception e) {
                        echo "no container to close"
                    }
                }
               
                // Copying settings.xml into .m2 folder
                sh 'cp ./backend/assets/settings.xml $HOME/.m2/settings.xml'
                sh 'cat  $HOME/.m2/settings.xml'

                sh 'chmod -R 777 ./'
                //sh 'docker images'
            }
        }
        stage('Export backend and cli') {
            when { 
                expression { "${skipSteps}" == 'false' }
            }
            steps {
                script {
                    if(env.BRANCH_NAME != 'main'){
                        directories.each { directory ->
                            stage ("Test $directory") {
                                if(env.BRANCH_NAME == 'Develop'){
                                    echo "$directory"
                                    dir("./$directory") {
                                        echo 'Testing...'
                                        sh 'mvn test'
                                    }
                                }
                            }
                            stage ("Building $directory") {
                                echo "$directory"
                                dir("./$directory") {
                                    echo 'Building...'
                                    sh 'mvn clean package'
                                }
                            }
                            stage ("Deploy $directory") {
                                if(env.BRANCH_NAME == 'Develop'){
                                    echo "$directory"
                                    dir("./$directory") {
                                        echo 'Deploying...'
                                        sh 'mvn deploy'
                                    }
                                }                                
                            }
                        }
                    }else{
                        directories.each { directory ->
                            stage ("Prepare and Perform release $directory") {
                                echo "$directory"
                                dir("./$directory") {
                                    echo 'Prepare and perform...'
                                    sh 'echo -e "\\n\\n\\n" | mvn release:prepare -Dresume=false'
                                    sh 'mvn release:perform'
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('Create dockers images') {
            when { 
                expression { "${skipSteps}" == 'false' }
            }
            steps {
                script {
                    directories.each { directory ->
                        stage ("Create $directory image") {
                            echo "$directory"
                            dir("./$directory") {
                                echo 'Testing...'
                                sh './build.sh'
                            }
                        }
                    }
                }
                sh 'docker images'
            }
        }
        stage('Start containers') {            
            when { 
                expression { "${containerWork}" == 'true' && "${skipSteps}" == 'false'} 
            }
            steps{
                sh 'docker ps'
                sh './build-all.sh'
                sh './run-all.sh'                        
            }
        }
        stage('Test end to end') {
            when { 
                expression { "${endToEndAvailable}" == 'true' && "${skipSteps}" == 'false' } }
            steps {
                sh './DevopsCli/endToEnd.sh'
            }
        }
        stage('Export images on DockerHub (main)') {
            when { 
                branch 'main'
                expression { "${skipSteps}" == 'false' } 
            }
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh './exportImages.sh'
            }
        }
    }
}