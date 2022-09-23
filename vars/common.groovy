def pipelineInit () {
    sh '''
        ls -ltr
        rm -rf .
        ls -ltr
        git 'https://github.com/devopsravi9/cart1.git\'
        ls -ltr
    '''
}