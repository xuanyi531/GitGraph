package helper;

/**
 * Created by Carol on 2018/11/27.
 */

public class TypeFilter{
    private static String[] PICS = {"bmp", "jpg", "jpeg", "png", "gif", "tga", "tif", "emf", "pic", "ico"};
    private static String[] VIDEOS = {"avi", "mpg", "mov", "mp4", "swf"};
    private static String[] AUDIOS = {"mp3", "wav", "aif", "au", "ram", "wma", "mmf", "amr", "aac", "flac"};
    private static String[] CUSTOM = {"xml", "yml", "js", "cpp", "c", "mk", "py", "html", "h", "gradle", "jar",
            "md", "gitignore", "properties", "apk"};

    private boolean filter_pic = false;
    private boolean filter_audio = false;
    private boolean filter_video = false;
    private boolean filter_custom = false;

    public TypeFilter(){
    }

    public void setPicFilter(boolean a){
        this.filter_pic = a;
    }

    public void setAudioFilter(boolean a){
        this.filter_audio = a;
    }

    public void setVideoFilter(boolean a){
        this.filter_video = a;
    }

    public void setCustomFilter(boolean a){
        this.filter_custom = a;
    }
    public boolean isFilter(String type){
        if (type == null)
            return true;

        if(filter_pic){
            for(String filter_type: PICS){
                if (type.equals(filter_type))
                    return true;
            }
        }
        if(filter_video){
            for(String filter_type: VIDEOS){
                if (type.equals(filter_type))
                    return true;
            }
        }
        if(filter_audio){
            for(String filter_type: AUDIOS){
                if (type.equals(filter_type))
                    return true;
            }
        }
        if(filter_custom){
            for(String filter_type: CUSTOM){
                if (type.equals(filter_type))
                    return true;
            }
        }
        return false;
    }
}
