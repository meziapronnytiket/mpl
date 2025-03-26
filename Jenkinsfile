@Library('MPL@main') _

TiketPipeline([
    agent_label: 'jenkins-agent',
    modules: [
        Checkout: [enabled: true],
        Test: [
            enabled: false,  // This module will be skipped
            type: 'unit',
            coverage: true
        ],
        Build: [
            enabled: true,
            type: 'docker'
        ],
        Scan: [
            enabled: true,
            type: 'sonarqube'
        ],
        Notification: [
            enabled: true,
            channel: '#jenkins-builds'
        ]
    ]
])
