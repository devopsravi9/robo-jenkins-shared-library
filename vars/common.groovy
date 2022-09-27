def pipelineInit () {
    stage ('clearing old content & git clone') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/devopsravi9/${GIT}.git"
        sh ' ls -ltr '
    }
}

def testing () {
    stage ('quality check & code check') {
        parallel ([
            codecheck: {
                withCredentials([usernamePassword(credentialsId: 'sonarqube', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    sh """
                        #sonar-scanner -Dsonar.projectKey=cart -Dsonar.host.url=http://3.238.52.216:9000 -Dsonar.login=${user} -Dsonar.password=${pass}  ${EXTRA_OPTS} -Dsonar.java.binaries=./target 
                        echo quality check
                    """
                }

                sh 'echo code check'
            },
            unittest: {
                unittest ()
            }
        ])
    }
}

def unittest () {

    if (env.APP_TYPE == 'nodejs') {

        sh """
            #npm test
            echo "run unit test"
        """
    }
    if (env.APP_TYPE == 'maven') {
        sh """
            #maven test
            echo "run unit test"
        """
    }
    if (env.APP_TYPE == 'python') {
        sh """
            #python -m unittest
            echo "run unit test"
        """
    }
    if (env.APP_TYPE == 'nginx') {
        sh """
            #npm run test
            echo "run unit test"
        """
    }
    }

def publishArtifact () {
    env.ENV = 'dev'
    stage('prepare  artifact ') {
        if (env.APP_TYPE == 'nodejs') {
            sh "zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip server.js node_modules"

        }
        if (env.APP_TYPE == 'maven') {
            sh " mv target/shipping-1.0.jar shipping.jar"
            sh "zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip  shipping.jar"
        }

        if (env.APP_TYPE == 'python') {
            sh """
                zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip *.py payment.ini requirements.txt
            """
        }

        if (env.APP_TYPE == 'nginx') {
            sh """
                cd static
                zip -r ../${ENV}-${COMPONENT}-${TAG_NAME}.zip  *
            """
        }
    }


    stage ('upload artifact to nexus repo') {
        withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'pass', usernameVariable: 'user')]) {
            sh """
                curl --fail -u ${user}:${pass} --upload-file ${ENV}-${COMPONENT}-${TAG_NAME}.zip http://172.31.8.168:8081/repository/${COMPONENT}/
            """

       }
   }

    stage ('deploy to dev env') {
        build job: 'deploy-any-env', parameters: [string(name: 'COMPONENT', value: "${COMPONENT}"), string(name: 'ENV', value: "${ENV}"), string(name: 'TAG_NAME', value: "${TAG_NAME}")]
    }

    stage ('run smoketests') {
        sh 'echo run smoke tests'
    }

    common.promoteRelease ("dev","qa")

    stage ('deploy to qa env') {
        sh 'echo deploy to qa environment'
    }

    stage ('run smoketests') {
        sh 'echo run smoke tests'
    }

    common.promoteRelease ("qa","prod")


}

def promoteRelease (SOURCE_ENV, ENV ) {
    stage ("upload ${ENV} articfact to nexus") {
        sh """
            cp ${SOURCE_ENV}-${COMPONENT}-${TAG_NAME}.zip ${ENV}-${COMPONENT}-${TAG_NAME}.zip
            curl --fail -u ${user}:${pass} --upload-file ${ENV}-${COMPONENT}-${TAG_NAME}.zip http://172.31.8.168:8081/repository/${COMPONENT}/
        """
    }

}



