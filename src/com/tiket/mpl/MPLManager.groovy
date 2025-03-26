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

        // Create a simplified execution context by directly exposing methods
        def binding = new Binding([
            echo: { msg -> steps.echo(msg) },
            sh: { cmd -> steps.sh(cmd) },
            container: { name, closure -> steps.container(name, closure) },
            config: config
        ])

        // Execute the script in the Pipeline context
        def shell = new GroovyShell(binding)
        shell.evaluate(moduleScript)
    }

    static def getModuleContainers(def steps, String name) {
        def containerPath = "${MODULE_PATH}/containers/${name}.groovy"
        try {
            def script = steps.libraryResource(containerPath)
            def result = steps.evaluate(script)
            if (result instanceof List) {
                return result.collect { container ->
                    containerTemplate(
                        name: container.name,
                        image: container.image,
                        command: container.command,
                        args: container.args ?: '',
                        ttyEnabled: container.ttyEnabled ?: false
                    )
                }
            }
            return []
        } catch (Exception e) {
            steps.echo "No containers defined for module ${name}"
            return []
        }
    }
}
