package model;

import helper.Utils;
import java.util.List;

public class MethodObject {
    private String name = null;
    private String body_md5 = null;
    private String file = null;
    private String clazz = null;
    private String body = null;
    private String sign_md5 = null;
    private List<ApiObject> apis = null;

    public MethodObject() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setBody(String body) {
        this.body = body;
        this.body_md5 = Utils.md5(body);
    }

    public String getName() {
        return name;
    }

    public String getBodyMd5() {
        return body_md5;
    }

    public String getFile() {
        return file;
    }

    public String getClazz() {
        return clazz;
    }

    public List<ApiObject> getApis() {
        return apis;
    }

    public void setApis(List<ApiObject> apis) {
        this.apis = apis;
    }

    public String getBody() {
        return body;
    }

    public String getSignMd5() {
        if (sign_md5 == null)
            sign_md5 = Utils.md5(file + clazz + name);
        return sign_md5;
    }
}
