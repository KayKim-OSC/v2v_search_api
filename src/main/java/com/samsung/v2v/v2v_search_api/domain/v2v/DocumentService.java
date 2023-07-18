package com.samsung.v2v.v2v_search_api.domain.v2v;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DocumentService {

    void registerTemplate(Map<String, String> templateFileMaps) throws IOException;

    Map<String,Object> searchDocumentList(DocumentCommand.RegisterRequest documentCommand);

    Map<String, Object> searchRedundantDocumentList(DocumentCommand.RegisterReplaceTitleRequest request);

    void deleteOldIndexDocuemnt(Map<String, List<String>> targetIndexList);


}
