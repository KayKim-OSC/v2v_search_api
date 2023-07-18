package com.samsung.v2v.v2v_search_api.common.util.json;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
#############################################################
* ClassName   : TemplateFileReader
* Description :  resources/indexTemplateFiles/ 경로
 *                  Index Template 파일 로딩
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/


@Component
public class TemplateFileReader {

    /*resources 파일에 존재하는 모든 template 파일 read*/
    public Map<String,String> readJsonFile() throws IOException {
        Map<String, String> result = new HashMap<>();

        String absolutePath = new File("").getAbsolutePath();
        File synonymDir = new File(absolutePath + "/config/indexTemplateFiles/");
        File[] synonymList = synonymDir.listFiles();
        /*synonym 적용버전 미적용 버전*/
        for(File synonym: synonymList){
            File[] eachLanguageDir = synonym.listFiles();
            if(eachLanguageDir!=null){
                for(File language:eachLanguageDir){
                    String fileName = Objects.requireNonNull(language.listFiles())[0].getName();
                    Path path = Objects.requireNonNull(language.listFiles())[0].toPath();
                    result.put(StringUtils.delete(fileName, ".json"), Files.readString(path));
                }
            }else{
                throw new IOException("template File이 존재하지 않습니다.");
            }
        }
        return result;
    }
}
