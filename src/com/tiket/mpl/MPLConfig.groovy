package com.tiket.mpl

class MPLConfig {
    private def config = [:]

    MPLConfig(def body = null, Map defaults = [:]) {
        config = defaults
        if (body) {
            def config_temp = [:]
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = config_temp
            body()
            config.putAll(config_temp)
        }
    }

    def getModulesList() {
        return config.modules ?: [:]
    }

    def getConfig() {
        return config
    }
}
