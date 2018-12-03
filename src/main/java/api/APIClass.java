package api;

import java.util.ArrayList;
import java.util.List;

public class APIClass {
    public String name = null;
    public String father = null;
    public List<String> methods = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public void addMethod(String method) {
        this.methods.add(method);
    }
}
