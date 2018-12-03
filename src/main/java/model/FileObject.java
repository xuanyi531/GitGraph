package model;


import helper.Utils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

/**
 * Created by Carol on 2018/11/27.
 */
public class FileObject {
    private String path;
    private String type;
    private String name;
    private ObjectId id;
    private String md5;
    private String filedata;

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getFiledata() {
        return filedata;
    }

    public ObjectId getId() {
        return id;
    }

    public FileObject(String path, String name, ObjectId id){
        this.path = path;
        this.name = name;
        this.id = id;
        if (name.lastIndexOf(".") != -1){
            this.type = name.substring(name.lastIndexOf(".") + 1);
        }else{
            this.type = null;
        }
    }

    public void setFiledata(String filedata) {
        this.filedata = filedata;
        this.md5 = Utils.md5(filedata);
    }

    public String getMd5() {
        return md5;
    }
}
