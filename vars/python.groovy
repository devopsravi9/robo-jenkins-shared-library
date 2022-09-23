def call () {
    node {
        common.pipelineInit ()

        common.publishArtifact ()
    }
}