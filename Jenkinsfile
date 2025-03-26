@Library('MPL@main') _

TiketPipeline([
    agent_label: 'jenkins-agent',
    modules: [
        Checkout: [
            branch: 'main',
            url: 'https://github.com/your-repo.git'
        ],
        Test: [
            type: 'unit',
            coverage: true
        ],
        Scan: [
            type: 'sonarqube'
        ],
        Notification: [
            channel: '#jenkins-builds',
            enabled: true
        ]
    ]
])
