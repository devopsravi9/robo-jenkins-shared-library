def call () {
    node () {
        stage ('pipeline init') {
            sh 'pipeline init'
        }
        stage ('pipeline plan') {
            sh 'pipeline plan'
        }
        stage ('pipeline apply') {
            sh 'pipeline apply -auto-approve'
        }
    }
}