package com.tiket.mpl

class MPLManager implements Serializable {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(def steps, String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]
        def foundPath = null

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            try {
                // Test if resource exists first
                def content = steps.libraryResource(modulePath)
                if (content) {
                    foundPath = modulePath
                    steps.echo "Found module at ${modulePath}"
                    break
                }
            } catch (Exception e) {
                steps.echo "Module not found in ${modulePath}"
                continue
            }
        }

        if (!foundPath) {
            steps.error("Module ${name} not found in paths: ${paths}")
        }
        return foundPath
    }

    static def executeModule(def steps, String modulePath) {
        def moduleScript = steps.libraryResource(modulePath)
        // Create a script object with binding
        def config = Helper.instance.getConfig().getConfig()
        def binding = new Binding([
            steps: steps,
            config: config,
            currentBuild: steps.currentBuild,
            env: steps.env
        ])

        // Load and execute the script
        def shell = new GroovyShell(binding)
        def script = shell.parse(moduleScript)
        script.run()
    }

    static def getModuleContainers(def steps, String name) {
        def containerPath = "${MODULE_PATH}/containers/${name}.groovy"
        try {
            def containerScript = steps.libraryResource(containerPath)
            def binding = new Binding([steps: steps])
            def shell = new GroovyShell(steps.getClass().getClassLoader(), binding)
            return shell.evaluate(containerScript)
        } catch (Exception e) {
            steps.echo "No containers defined for module ${name}"
            return []
        }
    }
}
