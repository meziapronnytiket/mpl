return [
    containerTemplate(
        name: 'sonar-scanner',
        image: 'sonarsource/sonar-scanner-cli',
        command: 'cat',
        ttyEnabled: true
    )
]
