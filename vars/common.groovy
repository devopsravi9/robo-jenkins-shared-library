def pipelineInit () {
    stage ('clearing old content & git clone') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/devopsravi9/${COMPONENT}.git"
    }
}

def publishArtifact () {
    stage ('prepare  artifact ') {
        if (env.APP_TYPE == 'nodejs') {
            sh "zip -r ${COMPONENT}-${TAG_NAME}.zip server.js node_modules"
        }
        //if (env.APP_TYPE == 'maven') {
          //  sh "zip -r ${COMPONENT}'.zip"
        //}

    stage ('upload artifact to nexus repo') {
        sh """
            curl --fail -u admin:admin123 --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.8.168:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}/
        """
    }


    }
}