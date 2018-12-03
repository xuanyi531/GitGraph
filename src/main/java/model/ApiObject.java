package model;

public class ApiObject {
    public String package_name;
    public String name;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign(){
        return package_name + "." + name;
    }
}
