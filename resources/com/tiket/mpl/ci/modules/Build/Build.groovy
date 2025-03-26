steps.echo "Building application..."

if (config.modules.Build?.type == 'docker') {
    steps.container('kaniko') {
        steps.sh """
            echo "Kaniko Build..."
            ls -la
        """
    }
} else {
    steps.echo "Using default build"
}
