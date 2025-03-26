@Library('MPL@main') _

TiketPipeline([
    agent_label: 'jenkins-agent',
    modules: [
        Checkout: [:],
        Test: [
            type: 'unit',
            coverage: true
        ],
        Build: [
            type: 'docker'
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
