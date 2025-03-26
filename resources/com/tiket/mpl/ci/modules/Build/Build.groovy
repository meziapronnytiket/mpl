steps.echo "Building application..."

if (MPL.getConfig().modules.Build?.type == 'docker') {
    steps.container('kaniko') {
        steps.sh "Kaniko Build..."
    }
} else {
    steps.echo "Using default build"
}
