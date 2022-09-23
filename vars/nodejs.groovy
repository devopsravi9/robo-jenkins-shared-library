def call () {
    node  {
        common.pipelineInit

        stage ('download dependencies') {
            sh 'npm install'
        }

        stage ('zip the file') {
            sh 'zip server.js '
            sh 'ls -ltr'
        }

        stage ('publish artifact') {
            sh ''
        }

    }
}