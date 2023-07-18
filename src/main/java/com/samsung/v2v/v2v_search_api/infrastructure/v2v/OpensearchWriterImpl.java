package com.samsung.v2v.v2v_search_api.infrastructure.v2v;

import com.samsung.v2v.v2v_search_api.domain.v2v.OpensearchWriter;
import lombok.RequiredArgsConstructor;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.opensearch.action.support.master.AcknowledgedResponse;
import org.opensearch.client.indices.PutIndexTemplateRequest;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OpensearchWriterImpl implements OpensearchWriter {

    public final OpensearchRepository opensearchRepository;


    @Override
    public boolean putIndexTemplate(String templateName,String fileContents) throws IOException {
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest(templateName);
        putIndexTemplateRequest.source(fileContents, XContentType.JSON);
        AcknowledgedResponse acknowledgedResponse =
                opensearchRepository.putIndexTemplate(putIndexTemplateRequest);
        return acknowledgedResponse.isAcknowledged();
    }

    @Override
    public boolean deleteIndex(DeleteIndexRequest deleteIndexRequest) throws IOException {
        AcknowledgedResponse acknowledgedResponse =
                opensearchRepository.deleteIndex(deleteIndexRequest);
        return acknowledgedResponse.isAcknowledged();
    }
}
