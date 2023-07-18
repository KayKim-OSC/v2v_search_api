package com.samsung.v2v.v2v_search_api.domain.v2v;

import java.io.IOException;
import java.util.List;

public interface OpensearchReader {

    boolean hasTemplate(String templateName) throws IOException;

    boolean hasIndex(String indexName) throws IOException;

    List<V2VContent> getDocumentList(DocumentCommand.RegisterRequest documentCommand);

    List<V2VContent> getRedundantDocumentList(DocumentCommand.RegisterReplaceTitleRequest request);

}
