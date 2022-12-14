def call () {
    node () {
        properties([
            parameters([
                choice(choices: ['dev', 'prod'], description: "choose environment ", name: "ENV"),
                choice(choices: ['apply', 'destroy'], description: "choose action ", name: "ACTION"),
            ]),
        ])

        stage ('code checkout') {
            sh 'find . | sed -e "1d" | xargs rm -rf *'
            git branch: 'main', url: "https://github.com/devopsravi9/${REPO_NAME}.git"
            sh 'ls -al'
        }

        stage ('terraform init') {
            sh 'terraform init -backend-config=env/${ENV}-backend.tfvars'
        }
        stage ('terraform plan') {
            sh 'terraform plan -var-file=env/${ENV}.tfvars'
        }
        stage ('terraform ${ACTION}') {
            sh 'terraform ${ACTION} -auto-approve -var-file=env/${ENV}.tfvars'
        }
    }
}
