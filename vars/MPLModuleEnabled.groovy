import com.tiket.mpl.Helper

def call(String name) {
    def mplConfig = Helper.instance.getConfig()
    def modules = mplConfig.getModulesList()
    if (!modules.containsKey(name)) {
        echo "Module ${name} is not defined in Jenkinsfile"
        return false
    }
    return modules[name].enabled != false
}
