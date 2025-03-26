import com.tiket.mpl.MPLManager

def call(Map params) {
    def MPL = MPLPipelineConfig(params, [
        agent_label: POD_LABEL,
        modules: [
            Checkout: [:],
            Test: [:],
            Build: [:],
            Scan: [:],
            Notification: [:]
        ]
    ])

    podTemplate(
        label: params.agent_label ?: POD_LABEL,
        containers: MPL.getContainers().collect { container ->
            containerTemplate(
                name: container.name,
                image: container.image,
                command: container.command,
                args: container.args,
                ttyEnabled: container.ttyEnabled
            )
        },
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

                if (MPLModuleEnabled("Build")) {
                    stage('Build') {
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
