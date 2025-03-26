import com.tiket.mpl.MPLConfig
import com.tiket.mpl.Helper
import com.tiket.mpl.MPLManager

def call(Map params = [:], Map defaults = [:]) {
    def config = new MPLConfig(params, defaults)
    Helper.instance.setConfig(config)

    // Centralized container management
    def requiredContainers = loadContainersForEnabledModules(config)
    config.setContainers(requiredContainers)
    return config
}

private def loadContainersForEnabledModules(config) {
    def containers = []
    def seenContainerNames = []

    config.getModulesList().each { name, moduleConfig ->
        if (moduleConfig != null && MPLModuleEnabled(name)) {
            try {
                def moduleContainers = MPLManager.getModuleContainers(this, name)
                moduleContainers.each { container ->
                    if (!seenContainerNames.contains(container.name)) {
                        seenContainerNames.add(container.name)
                        containers.add(container)
                    }
                }
            } catch (Exception e) {
                echo "Warning: Could not load containers for module ${name}: ${e.message}"
            }
        }
    }
    return containers
}
