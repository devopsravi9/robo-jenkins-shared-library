def pipelineInit () {
    sh '''
        ls -ltr
        rm -rf *
    '''
    git 'https://github.com/devopsravi9/cart1.git'
    sh 'ls -ltr'
}