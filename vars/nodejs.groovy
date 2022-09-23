def call () {
    node  {
        common.pipelineInit ()

        stage ('download dependencies') {
            sh 'npm install'
        }

        stage ('zip the file') {
            sh 'zip -r cart1.zip server.js node_modules'
            sh 'ls -ltr'
        }

        stage ('publish artifact') {
            sh ''
        }

    }
}