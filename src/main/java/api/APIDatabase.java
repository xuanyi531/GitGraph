package api;

import model.ApiObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIDatabase {
    public Map<String, APIClass> apimap = new HashMap<>();

    public APIDatabase(){

    }

    public void load_api(InputStream is){
        String content;
        try {
            content = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        String[] lines = content.split("\n");
        String package_name = null;
        String class_name;
        String class_path;
        String father_class = null;
        String method_name;
        APIClass apiClass = null;
        for (String line : lines){
            if (line.contains("package") && line.contains("{")){
                Pattern p = Pattern.compile("package\\s+(\\S+)\\s+\\{");
                Matcher m = p.matcher(line);
                if(m.find())
                    package_name = m.group(1);
            }

            else if (line.contains("class") && line.contains("{")){
                Pattern p = Pattern.compile("class\\s+(\\S+)\\s+");
                Matcher m = p.matcher(line);
                if(m.find()){
                    if (apiClass != null){
                        apimap.put(apiClass.getName(), apiClass);
                    }
                    apiClass = new APIClass();
                    class_name = m.group(1);
                    class_path = package_name + "." + class_name;
                    apiClass.setName(class_path);
                    if (line.contains("extends")){
                        p = Pattern.compile("extends\\s+(\\S+)\\s+");
                        m = p.matcher(line);
                        if(m.find()) {
                            father_class = m.group(1);
                            apiClass.setFather(father_class);
                        }
                    }
                }

            }
            else if (line.contains("method ") && line.contains("(")){
                Pattern p = Pattern.compile("\\s+(\\w+)\\(");
                Matcher m = p.matcher(line);
                if(m.find()) {
                    method_name = m.group(1);
                    apiClass.addMethod(method_name);
                }
            }

        }
        if (apiClass != null){
            apimap.put(apiClass.getName(), apiClass);
        }
    }

    public ApiObject checkApi(String package_name, String function) {
        ApiObject apiObject;
        if(package_name.contains("*")){
            String package_part = package_name.substring(package_name.indexOf("*") - 1);
            for (String package_possible: apimap.keySet()){
                if (package_possible.startsWith(package_part)){
                    ApiObject result = checkApi(package_possible, function);
                    if(result != null)
                        return result;
                }

            }
        }
        else if (apimap.containsKey(package_name)) {
            APIClass apiClass = apimap.get(package_name);
            List<String> methods = apiClass.getMethods();
            for (String method : methods) {
                if (method.equals(function)){
                    apiObject = new ApiObject();
                    apiObject.setName(function);
                    apiObject.setPackage_name(package_name);
                    return apiObject;
                }
                else if(apiClass.getFather() != null)
                    return checkApi(apiClass.getFather(), function);
            }
        }
        return null;
    }

    public ApiObject isApi(List<String> imports, String function) {
        for (String import_name: imports){
            ApiObject apiObject = checkApi(import_name, function);
            if(apiObject != null)
                return apiObject;
        }
        return null;
    }
}
