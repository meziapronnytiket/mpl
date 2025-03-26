return [
    containerTemplate(
        name: 'node',
        image: 'node:16',
        command: 'cat',
        ttyEnabled: true
    )
]
