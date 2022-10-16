def call () {
    env.EXTRA_OPTS=""
    node  {

        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        common.testing ()

        if (env.BRANCH_NAME == env.TAG_NAME) {
            //common.publishArtifact()
            common.publishLoacalArtifact()
            common.createAMI()
        }


    }
}