import com.tiket.mpl.MPLManager

def call(String name = env.STAGE_NAME) {
    def modulePath = MPLManager.loadModule(this, name)
    MPLManager.executeModule(this, modulePath)
}
