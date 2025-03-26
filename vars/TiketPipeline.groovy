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

    // Get active modules and their containers
    def activeModules = []
    def requiredContainers = []

    params.modules.each { name, config ->
        if (MPLModuleEnabled(name)) {
            activeModules << name
            // Load module to get its container requirements
            def moduleContainers = MPLManager.getModuleContainers(this, name)
            if (moduleContainers) {
                requiredContainers.addAll(moduleContainers)
            }
        }
    }

    podTemplate(
        containers: requiredContainers.unique { it.name },
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
