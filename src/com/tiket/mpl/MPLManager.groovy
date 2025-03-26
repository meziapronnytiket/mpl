package com.tiket.mpl

class MPLManager implements Serializable {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(def steps, String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            try {
                if (steps.fileExists("resources/${modulePath}")) {
                    steps.libraryResource(modulePath)
                    return modulePath
                }
            } catch (Exception e) {
                steps.echo "Checking next path for module ${name}"
                continue
            }
        }
        steps.error("Module ${name} not found in paths: ${paths}")
    }

    static def executeModule(def steps, String modulePath) {
        def moduleScript = steps.libraryResource(modulePath)
        def shell = new GroovyShell(steps.getClass().getClassLoader())
        shell.evaluate(moduleScript)
    }
}
