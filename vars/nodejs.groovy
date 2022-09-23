def call () {
    node  {
        options {
            ansiColor('xterm')
        }
        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        common.publishArtifact ()


    }
}