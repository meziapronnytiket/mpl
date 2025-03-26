package com.tiket.mpl

@Singleton
class Helper {
    private MPLConfig mplConfig

    void setConfig(MPLConfig config) {
        this.mplConfig = config
    }

    MPLConfig getConfig() {
        return this.mplConfig
    }
}
