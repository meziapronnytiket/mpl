import com.tiket.mpl.Helper

def call(String name) {
    def mplConfig = Helper.instance.getConfig()
    def modules = mplConfig.getModulesList()
    return modules.containsKey(name)
}
