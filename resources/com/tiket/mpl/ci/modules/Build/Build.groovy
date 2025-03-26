echo "Building application..."

if (config.modules.Build?.type == 'docker') {
    container('kaniko') {
        sh """
            echo "Kaniko Build..."
            ls -la
        """
    }
} else {
    echo "Using default build"
}
