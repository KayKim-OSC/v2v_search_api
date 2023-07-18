package com.samsung.v2v.v2v_search_api.application;

import com.samsung.v2v.v2v_search_api.common.util.json.TemplateFileReader;
import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
#############################################################
* ClassName   : PreApplicationRunner
* Description : index_template 파일 등록 클래스
 *              Opensearch에 index_template 파일이 없을 경우 생성
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/


@Component
@RequiredArgsConstructor
@Slf4j
public class PreApplicationRunner implements ApplicationRunner {

    private final TemplateFileReader templateFileReader;
    private final DocumentService documentService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("* Each Lanugage IndexTemplateFile Load start");
        log.info("====================================================================================================");
        this._putIndexTemplateToServer();
        log.info("====================================================================================================");
    }

    /**
    #############################################################
    * MethodName   : _putIndexTemplateToServer
    * Description : resources/indexTemplateFiles에 선언한 Template 파일 Opensearch에 등록
    * Parameter   :
    * Return      : void
    #############################################################
    */

    private void _putIndexTemplateToServer() throws IOException {
        Map<String, String> templateFileMaps = templateFileReader.readJsonFile();
        documentService.registerTemplate(templateFileMaps);
    }
}
