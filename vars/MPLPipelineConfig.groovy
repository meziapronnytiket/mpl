import com.tiket.mpl.MPLConfig
import com.tiket.mpl.Helper
import com.tiket.mpl.MPLManager

def call(Map params = [:], Map defaults = [:]) {
    def config = new MPLConfig(params, defaults)
    Helper.instance.setConfig(config)

    // Load containers for active modules
    def requiredContainers = []
    config.getModulesList().each { name, moduleConfig ->
        if (moduleConfig != null) {
            try {
                def moduleContainers = MPLManager.getModuleContainers(this, name)
                if (moduleContainers) {
                    requiredContainers.addAll(moduleContainers)
                }
            } catch (Exception e) {
                echo "Warning: Could not load containers for module ${name}: ${e.message}"
            }
        }
    }

    // Store containers in config for later use
    config.setContainers(requiredContainers.unique { it.name })

    return config
}
