def call () {
    node {

        common.pipelineInit ()

        stage ('build the package') {
            sh 'mvn clean package'
        }

        if (env.BRANCH_NAME == env.TAG_NAME) {
            common.publishArtifact()
        }
    }
}