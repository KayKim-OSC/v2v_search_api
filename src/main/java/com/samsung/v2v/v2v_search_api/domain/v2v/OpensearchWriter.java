package com.samsung.v2v.v2v_search_api.domain.v2v;

import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;

import java.io.IOException;

public interface OpensearchWriter {

    boolean putIndexTemplate(String templateName, String fileContents) throws IOException;

    boolean deleteIndex(DeleteIndexRequest deleteIndexRequest) throws IOException;

}