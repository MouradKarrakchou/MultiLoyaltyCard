def directories = [
        "backend",
        "cli"
]

pipeline {
    agent any

    environment {
		DOCKERHUB_CREDENTIALS=credentials('dockerhub-cred')
        GITHUB_CREDENTIALS=credentials('KilianBonnet-GitHub-creds')
		containerWork = false
		endToEndAvailable = false
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
                }
                
                // Cleaning .m2 folder
                sh 'if [ -d "$HOME/.m2" ]; then rm -rf $HOME/.m2; fi'
                sh 'mkdir $HOME/.m2'

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
                                echo "$directory"
                                dir("./$directory") {
                                    echo 'Testing...'
                                    sh 'mvn test'
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
                                echo "$directory"
                                dir("./$directory") {
                                    echo 'Deploying...'
                                    sh 'mvn deploy'
                                }
                            }
                        }
                    }else{
                        directories.each { directory ->
                            stage ("Prepare and Perform release $directory") {
                                echo "$directory"

                                dir("./$directory") {
                                    // Check for unpushed modifications
                                    def unpushedChanges = sh(returnStatus: true, script: 'git diff --exit-code && git diff --cached --exit-code')
                                    if (unpushedChanges != 0) {
                                        withCredentials([usernamePassword(credentialsId: 'KilianBonnet-GitHub-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                            sh 'git add .'
                                            sh 'git -c credential.username=${{USERNAME}} -c credential.helper=store pull -m "Jenkins auto-validation"'
                                            sh 'git -c credential.username=${{USERNAME}} -c credential.helper=store push'
                                        }
                                    }

                                    // Performing release
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
            steps {
                //sh './build-all.sh'
                sh './run-all.sh'
            }
        }
        stage('Test end to end') {
            when { 
                expression { "${endToEndAvailable}" == 'true' && "${skipSteps}" == 'false' } }
            steps {

                sh './endToEnd.sh'
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