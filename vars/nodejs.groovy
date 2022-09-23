def call () {
    node  {
        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        common.publishArtifact ()


    }
}