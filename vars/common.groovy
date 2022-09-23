def pipelineInit () {
    git branch: 'main', url: 'https://github.com/devopsravi9/cart1.git'
    sh 'ls -ltr'
}