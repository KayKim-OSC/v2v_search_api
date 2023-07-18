package com.samsung.v2v.v2v_search_api.domain.v2v;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
public class V2VContent {


    private String docId;
    private String workDate;
    private String docDate;
    private Long colctDate;
    private Long colctDt;
    private String weeks;
    private String channelCode;
    private String langCode;
    private String orgLangCode;
    private String orgLangName;
    private String url;
    private String urlRef;
    private String summary;
    private String title;
    private String replaceTitle;
    private String docCn;
    private String orgTitle;
    private String orgDocCn;
    private String onlineStoreDocCn;
    private String firstRegrDateTime;
    private String firstRegrId;
    private String lastModDateTime;
    private String lastModrId;
    private String colctmon;
    /*TN_SAS_DOC_TOTAL N*/
    private List<String> docPrdId;
    private List<String> channelType;
    private List<String> countryCode;
    private List<String> countryName;
    private List<String> siteId;
    private List<String> siteName;
    private List<String> storeRate;
    private List<String> transYn;
    private List<String> onlineStoreYn;
    private List<String> categoryCode;
    private List<String> categoryLength;
    private List<String> categoryType;
    private List<String> gbm;
    private List<String> company;
    private List<String> productCode;
    private List<String> productName;
    private List<String> productKeyword;
    private List<String> senti;
    private List<String> opinionKwd;
    private List<String> featureKwd;
    private List<String> problemKr;
    private List<String> problemEn;
    private List<String> componentLv1;
    private List<String> componentLv2;
    private List<String> mainType;
    private List<String> mainTypeName;
    private List<String> modelName;
    private List<String> appId;
    private List<String> appName;
    private List<String> osVer;
    private List<String> fwVer;
    private List<String> appVer;
    private List<String> csc;
    private List<String> level3;
    private List<String> vf1;
    private List<String> vf2;
    private List<String> riskId;
    private Long dupicateCount;
    private long totalCount;

}
