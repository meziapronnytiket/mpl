import com.tiket.mpl.MPLManager
import com.tiket.mpl.Helper

def call(String name = env.STAGE_NAME) {
    def modulePath = MPLManager.loadModule(this, name)
    def config = Helper.instance.getConfig().getConfig()
    MPLManager.executeModule(this, modulePath, config)
}
