package com.samsung.v2v.v2v_search_api.domain.v2v;

import com.samsung.v2v.v2v_search_api.common.constants.CommonKey;
import com.samsung.v2v.v2v_search_api.common.constants.LanguageCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final OpensearchReader opensearchReader;
    private final OpensearchWriter opensearchWriter;

    @Override
    public void registerTemplate(Map<String, String> templateFileMaps) throws IOException {

        for (String templateName : templateFileMaps.keySet()) {
            if (!opensearchReader.hasTemplate(templateName)) {
                boolean result = opensearchWriter.putIndexTemplate(templateName, templateFileMaps.get(templateName));
                log.info("PUT language= [{}] template name= [{}] to opensearch server, result = {}",
                        LanguageCode.valueOfFileName(templateName) ,templateName, result);
            } else {
                log.info("language= [{}] template name= [{}] is aleary existed.",
                        LanguageCode.valueOfFileName(templateName),templateName);
            }
        }
    }

    @Override
    public Map<String,Object> searchDocumentList(DocumentCommand.RegisterRequest documentCommand) {
        Map<String, Object> result = new HashMap<>();
        List<V2VContent> documentList = opensearchReader.getDocumentList(documentCommand);
        long totalCount = documentList.get(0).getTotalCount();
        List<DocumentInfo> collect = documentList.stream().map(DocumentInfo::new).collect(Collectors.toList());
        result.put(CommonKey.COUNT_KEY.getValue(), totalCount);
        result.put(CommonKey.DATA_KEY.getValue(), collect);
        return result;
    }

    @Override
    public Map<String, Object> searchRedundantDocumentList(DocumentCommand.RegisterReplaceTitleRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<V2VContent> documentList = opensearchReader.getRedundantDocumentList(request);
        long totalCount = documentList.get(0).getTotalCount();
        List<DocumentInfo> collect = documentList.stream().map(DocumentInfo::new).collect(Collectors.toList());
        result.put(CommonKey.COUNT_KEY.getValue(), totalCount);
        result.put(CommonKey.DATA_KEY.getValue(), collect);
        return result;
    }


    @Override
    public void deleteOldIndexDocuemnt(Map<String, List<String>> targetIndexList) {
        boolean result;
        for(String key : targetIndexList.keySet()){
            result = false;
            List<String> indexList = targetIndexList.get(key);
            for(String indexName :indexList){
                try {
                    if(opensearchReader.hasIndex(indexName)){
                        result = opensearchWriter.deleteIndex(new DeleteIndexRequest(indexName));
                    }
                } catch (IOException e) {
                    log.error("Target Index = [{}] delete failed becauese = [{}]", indexName, e.getMessage());
                }
                log.info("Delete Task Result ( LangCode = [{}], indexName = [{}], result=[{}] )",key, indexName, result);
            }
        }
        
    }
}
