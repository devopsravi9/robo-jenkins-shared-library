def call () {
    node {

        common.pipelineInit ()

        stage ('build the package') {
            sh 'mvn clean package'
        }

        common.publishArtifact ()
    }
}