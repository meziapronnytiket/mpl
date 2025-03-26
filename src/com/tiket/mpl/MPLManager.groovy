package com.tiket.mpl

class MPLManager implements Serializable {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(def steps, String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            try {
                steps.libraryResource(modulePath)
                steps.echo "Found module at ${modulePath}"
                return modulePath
            } catch (Exception e) {
                steps.echo "Module not found in ${modulePath}"
            }
        }
        steps.error("Module ${name} not found in paths: ${paths}")
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
