return [
    containerTemplate(
        name: 'git',
        image: 'alpine/git',
        command: 'cat',
        ttyEnabled: true
    )
]
