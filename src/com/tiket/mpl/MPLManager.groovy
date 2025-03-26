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

    static def executeModule(def steps, String modulePath, def config) {
        def moduleScript = steps.libraryResource(modulePath)

        // Directly evaluate the script in the Pipeline context
        steps.evaluate("""
            def echo = { msg -> steps.echo(msg) }
            def sh = { cmd -> steps.sh(cmd) }
            def container = { name, closure -> steps.container(name, closure) }
            def config = ${config.inspect()}
            ${moduleScript}
        """)
    }

    static def getModuleContainers(def steps, String name) {
        def containerPath = "${MODULE_PATH}/containers/${name}.groovy"
        try {
            def script = steps.libraryResource(containerPath)
            def result = steps.evaluate(script)
            if (result instanceof List) {
                return result
            }
            return []
        } catch (Exception e) {
            steps.echo "No containers defined for module ${name}"
            return []
        }
    }
}
