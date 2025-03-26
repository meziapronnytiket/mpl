import com.tiket.mpl.MPLConfig
import com.tiket.mpl.Helper
import com.tiket.mpl.MPLManager

def call(Map params = [:], Map defaults = [:]) {
    def config = new MPLConfig(params, defaults)
    Helper.instance.setConfig(config)

    // Load containers for active modules
    def requiredContainers = []
    def seenContainers = [] // Track container names to avoid duplicates

    config.getModulesList().each { name, moduleConfig ->
        if (moduleConfig != null) {
            try {
                def containers = MPLManager.getModuleContainers(this, name)
                containers.each { container ->
                    if (!(container.name in seenContainers)) {
                        seenContainers.add(container.name)
                        requiredContainers.add(container)
                    }
                }
            } catch (Exception e) {
                echo "Warning: Could not load containers for module ${name}: ${e.message}"
            }
        }
    }

    config.setContainers(requiredContainers)
    return config
}
