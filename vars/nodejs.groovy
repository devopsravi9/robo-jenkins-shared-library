def call () {
    node  {

        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        if (env.BRANCH_NAME == env.TAG_NAME) {
            common.publishArtifact()
        }


    }
}