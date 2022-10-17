def call () {
    env.EXTRA_OPTS='-Dsonar.java.binaries=./target'
    node {

        common.pipelineInit ()
        if ( env.BRANCH_NAME == env.TAG_NAME ) {
            sh 'git checkout ${TAG_NAME}'
        }

        stage ('build the package') {
            sh 'mvn clean package'
        }
        common.testing ()

        if (env.BRANCH_NAME == env.TAG_NAME) {
            //common.publishArtifact()
            common.publishLocalArtifact()
            common.createAMI()
        }
    }
}