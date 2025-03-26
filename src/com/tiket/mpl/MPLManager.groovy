package com.tiket.mpl

class MPLManager implements Serializable {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(def steps, String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            try {
                steps.libraryResource(modulePath)
                return modulePath
            } catch (Exception e) {
                steps.echo "Module not found in ${modulePath}"
                continue
            }
        }
        throw new Exception("Module ${name} not found in any path")
    }

    static def executeModule(def steps, String modulePath) {
        def moduleScript = steps.libraryResource(modulePath)
        steps.evaluate(moduleScript)
    }
}
