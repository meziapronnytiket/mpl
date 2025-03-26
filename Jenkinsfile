@Library('MPL@master') _

TiketPipeline {
    agent_label = 'jenkins-agent'
    modules = [
        Checkout: [
            branch: 'main',
            url: 'https://github.com/your-repo.git'
        ],
        Test: [
            type: 'unit',
            coverage: true
        ],
        Build: [
            type: 'maven',
            goals: 'clean package'
        ],
        Scan: [
            type: 'sonarqube'
        ],
        Notification: [
            channel: '#jenkins-builds',
            enabled: true
        ]
    ]
}
