return [
    containerTemplate(
        name: 'kaniko',
        image: 'gcr.io/kaniko-project/executor:v1.23.2-debug',
        command: 'cat',
        args: '',
        ttyEnabled: true
    )
]
