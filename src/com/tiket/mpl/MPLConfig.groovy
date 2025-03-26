package com.tiket.mpl

class MPLConfig implements Serializable {
    private def config = [:]

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
}
