def call () {
    node  {

        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        common.testing

        if (env.BRANCH_NAME == env.TAG_NAME) {
            common.publishArtifact()
        }


    }
}