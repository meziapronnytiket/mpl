package com.tiket.mpl

class MPLManager {
    private static final String MODULE_PATH = 'com/tiket/mpl'

    static def loadModule(String name) {
        def paths = ["ci/modules/${name}", "cd/modules/${name}", "shared/modules/${name}"]

        for (path in paths) {
            def modulePath = "${MODULE_PATH}/${path}/${name}.groovy"
            if (libraryResource(modulePath)) {
                return modulePath
            }
        }
        throw new Exception("Module ${name} not found")
    }

    static def executeModule(String modulePath) {
        def moduleScript = libraryResource(modulePath)
        evaluate(moduleScript)
    }
}
