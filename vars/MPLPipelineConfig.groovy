import com.tiket.mpl.MPLConfig
import com.tiket.mpl.Helper

def call(Map params = [:], Map defaults = [:]) {
    def config = new MPLConfig(params, defaults)
    Helper.instance.setConfig(config)
    return config
}
