package com.tiket.mpl

class MPLManager implements Serializable {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(def steps, String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]
        def foundPath = null

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            try {
                steps.libraryResource(modulePath)
                foundPath = modulePath
                steps.echo "Found module at ${modulePath}"
                break
            } catch (Exception e) {
                steps.echo "Module not found in ${modulePath}"
            }
        }

        if (!foundPath) {
            steps.error("Module ${name} not found in paths: ${paths}")
        }
        return foundPath
    }

    static def executeModule(def steps, String modulePath) {
        def moduleScript = steps.libraryResource(modulePath)
        def config = Helper.instance.getConfig()

        // Execute the script in the Pipeline context
        steps.evaluate("""
            def config = binding.getVariable('config')
            ${moduleScript}
        """)
    }

    static def getModuleContainers(def steps, String name) {
        def containerPath = "${MODULE_PATH}/containers/${name}.groovy"
        try {
            def script = steps.libraryResource(containerPath)
            return steps.evaluate(script)
        } catch (Exception e) {
            steps.echo "No containers defined for module ${name}"
            return []
        }
    }
}
