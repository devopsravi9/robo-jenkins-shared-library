def call () {
    node {
        options {
            ansiColor('xterm')
        }
        common.pipelineInit ()

        stage ('build the package') {
            sh 'mvn clean package'
            sh 'ls -ltr'
        }

        common.publishArtifact ()
    }
}