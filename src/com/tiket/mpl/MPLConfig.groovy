package com.tiket.mpl

class MPLConfig implements Serializable {
    private def config = [:]
    private def containers = []

    MPLConfig(Map params = [:], Map defaults = [:]) {
        config = defaults.clone()
        config.putAll(params)
    }

    def getModulesList() {
        return config.modules ?: [:]
    }

    def getConfig() {
        return config
    }

    def getContainerTemplates(List moduleNames) {
        def containers = []
        moduleNames.each { name ->
            if (config.modules[name]?.containers) {
                containers.addAll(config.modules[name].containers)
            }
        }
        return containers.unique { it.name }
    }

    def setContainers(def containerList) {
        containers = containerList
    }

    def getContainers() {
        return containers
    }
}
