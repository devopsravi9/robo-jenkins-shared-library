def pipelineInit () {
    stage ('clearing old content & git clone') {
        sh 'rm -rf *'
        git branch: 'main', url: 'https://github.com/devopsravi9/${ COMPONENT }.git'
    }
}

def publishArtifact () {
    stage ('prepare  artifact ') {
        if (env.APP_TYPE == 'nodejs') {
            sh 'zip -r ${ COMPONENT }.zip server.js node_modules'
        }
        //if (env.APP_TYPE == 'maven') {
          //  sh 'zip -r ${COMPONENT}'.zip
        //}


    }
}