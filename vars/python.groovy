def call () {
    env.EXTRA_OPTS=""
    node {

        common.pipelineInit ()
        common.testing ()

        if (env.BRANCH_NAME == env.TAG_NAME) {
            common.publishArtifact()
        }
    }
}