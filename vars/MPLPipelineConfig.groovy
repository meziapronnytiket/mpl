import com.tiket.mpl.MPLConfig
import com.tiket.mpl.Helper

def call(body, Map defaults = [:]) {
    def config = new MPLConfig(body, defaults)
    Helper.instance.setConfig(config)
    return config
}
