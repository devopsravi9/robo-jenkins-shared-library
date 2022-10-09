def call () {
    node () {
        stage ('pipeline init') {
            sh 'terraform init'
        }
        stage ('pipeline plan') {
            sh 'terraform plan'
        }
        stage ('pipeline apply') {
            sh 'terraform apply -auto-approve'
        }
    }
}