package com.samsung.v2v.v2v_search_api.interfaces.v2v;

import com.samsung.v2v.v2v_search_api.common.constants.CommonKey;
import com.samsung.v2v.v2v_search_api.common.response.CommonResponse;
import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentInfo;
import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final DocumentService documentService;



    @PostMapping("/search")
    public CommonResponse search(@RequestBody @Valid SearchDto.RegisterRequest request) {
        var requestCommand = request.toCommand();
        var documentInfos = documentService.searchDocumentList(requestCommand);
        var totalCount = (Long)documentInfos.get(CommonKey.COUNT_KEY.getValue());
        var data = (List<DocumentInfo>) documentInfos.get(CommonKey.DATA_KEY.getValue());
        var response = new SearchDto.RegisterResponse(data,data.size());
        return CommonResponse.success(response,totalCount);
    }

    @PostMapping ("/search/detail")
    public CommonResponse searchRedundantDetail(@RequestBody @Valid SearchDto.RegisterReplaceTitleRequest request){

        var requestCommand = request.toCommand();
        var documentInfos = documentService.searchRedundantDocumentList(requestCommand);
        var totalCount = (Long)documentInfos.get(CommonKey.COUNT_KEY.getValue());
        var data = (List<DocumentInfo>) documentInfos.get(CommonKey.DATA_KEY.getValue());
        var response = new SearchDto.RegisterResponse(data,data.size());
        return CommonResponse.success(response,totalCount);
    }

}
