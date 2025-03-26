def trivy(String name){
    container(name: 'trivy') {
        echo "Trivy scanning"
    }
}