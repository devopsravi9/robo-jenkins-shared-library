def call () {
    node () {
        properties([
            parameters([
                choice(choices: ['dev\nprod'], description: "choose environment ", name: "ENV"),
                choice(choices: ['apply\ndestroy'], description: "choose action ", name: "ACTION"),
            ]),
        ])

        stage ('terraform init') {
            sh 'terraform init -backend-config=env/${ENV}-backend.tfvars'
        }
        stage ('terraform plan') {
            sh 'terraform plan -backend-config=env/${ENV}-backend.tfvars'
        }
        stage ('terraform ${ACTION}') {
            sh 'terraform ${ACTION} -auto-approve -var-file=env/${ENV}.tfvars'
        }
    }
}
