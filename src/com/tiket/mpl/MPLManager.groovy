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
        def binding = new Binding([
            steps: steps,
            MPL: Helper.instance.getConfig()
        ])
        def shell = new GroovyShell(steps.getClass().getClassLoader(), binding)
        shell.evaluate(moduleScript)
    }
}
