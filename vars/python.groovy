def call () {
    node {
        options {
            ansiColor('xterm')
        }
        common.pipelineInit ()

        common.publishArtifact ()
    }
}