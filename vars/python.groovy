def call () {
    env.EXTRA_OPTS=""
    node ('ci') {

        common.pipelineInit ()
        if ( env.BRANCH_NAME == env.TAG_NAME ) {
            sh 'git checkout ${TAG_NAME}'
        }

        common.testing ()

        if (env.BRANCH_NAME == env.TAG_NAME) {
            //common.publishArtifact()
            common.publishLoacalArtifact()
            common.createAMI()
        }
    }
}