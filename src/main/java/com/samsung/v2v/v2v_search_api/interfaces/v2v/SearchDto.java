package com.samsung.v2v.v2v_search_api.interfaces.v2v;

import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SearchDto {


    @Getter
    @Builder
    @ToString
    public static class RegisterRequest {

        @NotNull(message = "sDate value is null")
        private String sDate;
        @NotNull(message = "eDate value is null")
        private String eDate;
        @NotNull(message = "orgLangCode value is null")
        private String orgLangCode;
        @NotNull(message = "orderBy value is null")
        private String orderBy;
        @NotNull(message = "duplicateYn value is null")
        private String duplicateYn;
        @NotNull(message = "pageIndex value is null")
        private int pageIndex;
        @NotNull(message = "pageSize value is null")
        private int pageSize;
        @NotNull(message = "keyword value is null")
        private String keyword;
        @NotNull(message="addKeyWords[] value is null")
        private List<AddKeywordInfo> addKeyWords;
        @NotNull(message = "synonymYn value is null")
        private String synonymYn;
        @NotNull(message = "types[] value is null")
        private List<String> types;
        @NotNull(message = "productCodes[] value is null")
        private List<String> productCodes;
        @NotNull(message = "channelCodes[] value is null")
        private List<String> channelCodes;
        @NotNull(message = "componentCodes[] value is null")
        private List<String> componentCodes;
        @NotNull(message = "opinionCodes[] value is null")
        private List<String> opinionCodes;
        @NotNull(message = "siteCodes[] value is null")
        private List<String> siteCodes;
        @NotNull(message = "itemCodes[] value is null")
        private List<String> itemCodes;
        @NotNull(message = "gbms[] value is null")
        private List<String> gbms;
        @NotNull(message = "makers[] value is null")
        private List<String> makers;
        @NotNull(message = "mainTypes[] value is null")
        private List<String> mainTypes;
        @NotNull(message = "osVers[] value is null")
        private List<String> osVers;
        @NotNull(message = "fwVers[] value is null")
        private List<String> fwVers;
        @NotNull(message = "appCodes[] value is null")
        private List<String> appCodes;

        public DocumentCommand.RegisterRequest toCommand() {
            return new DocumentCommand.RegisterRequest(this);
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterReplaceTitleRequest{

        @NotNull(message = "sDate value is null")
        private String sDate;
        @NotNull(message = "eDate value is null")
        private String eDate;
        @NotNull(message = "orgLangCode value is null")
        private String orgLangCode;
        @NotNull(message = "pageIndex value is null")
        private int pageIndex;
        @NotNull(message = "pageSize value is null")
        private int pageSize;
        @NotNull(message = "synonymYn value is null")
        private String synonymYn;
        @NotNull(message = "replaceTitle value is null")
        private String replaceTitle;

        public DocumentCommand.RegisterReplaceTitleRequest toCommand(){return new DocumentCommand.RegisterReplaceTitleRequest(this);}

    }

    @Getter
    @ToString
    @Builder
    public static class AddKeywordInfo{
        private String operator;
        private String keyword;
    }

    @Getter
    @ToString
    public static class RegisterResponse<T>{
        long count;
        T document;
        public RegisterResponse(T documents,int count){
            this.count = count;
            this.document = documents;
        }
    }
}
