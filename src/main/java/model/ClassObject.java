package model;

import helper.Utils;

import java.util.List;

public class ClassObject {
    private String package_name = "";
    private String name;
    private String file;
    private String sign_md5;
    private String body_md5;
    private List<MethodObject> methods;

    public ClassObject(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSignMd5() {
        if (sign_md5 == null)
            sign_md5 = Utils.md5(file + name);
        return sign_md5;
    }

    public String getBodyMd5() {
        return body_md5;
    }

    public void setBody(String body) {
        this.body_md5 = Utils.md5(body);
    }

    public List<MethodObject> getMethods(){
        return methods;
    }

    public void setMethods(List<MethodObject> methods){
        this.methods = methods;
    }

    public String getPackageName() {
        return package_name;
    }

    public void setPackageName(String package_name) {
        this.package_name = package_name;
    }
}
