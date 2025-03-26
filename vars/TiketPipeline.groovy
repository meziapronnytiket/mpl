def call(Map params) {
    def MPL = MPLPipelineConfig(params, [
        agent_label: 'jenkins-agent',
        modules: [
            Checkout: [:],
            Test: [:],
            Build: [:],
            Scan: [:],
            Notification: [:]
        ]
    ])

    podTemplate(
        containers: [
            containerTemplate(
                name: 'busybox',
                image: 'busybox',
                command: 'cat',
                ttyEnabled: true)
        ],
        serviceAccount: 'jenkins-service-account'
    ) {
        node(POD_LABEL) {
            def pipelineResult = 'SUCCESS'
            try {
                stage('Checkout') {
                    if (MPLModuleEnabled("Checkout")) {
                        MPLModule('Checkout')
                    }
                }

                stage('Test') {
                    if (MPLModuleEnabled("Test")) {
                        MPLModule('Test')
                    }
                }

                stage('Build') {
                    if (MPLModuleEnabled("Build")) {
                        MPLModule('Build')
                    }
                }

                stage('Scan') {
                    if (MPLModuleEnabled("Scan")) {
                        MPLModule('Scan')
                    }
                }
            } catch (err) {
                pipelineResult = 'FAILURE'
                throw err
            } finally {
                stage('Notification') {
                    if (MPLModuleEnabled("Notification")) {
                        MPLModule('Notification')
                    }
                }
            }
        }
    }
}
