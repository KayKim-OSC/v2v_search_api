package com.samsung.v2v.v2v_search_api.domain.v2v;

import lombok.Getter;
import org.springframework.context.annotation.Description;

import java.util.List;

@Description("Document Response Data")
@Getter
public class DocumentInfo {

    private String docId;
    private Long colctDate;
    private Long colctDt;
    private String channelCode;
    private String title;
    private String replaceTitle;
    private String orgTitle;
    private String summary;
    private String docCn;
    private String orgDocCn;
    private String url;
    private String weeks;
    private String langCode;
    private String orgLangCode;
    private String orgLangName;
    private List<String> countryName;
    private List<String> siteName;
    private List<String> productCode;
    private List<String> productName;
    private List<String> componentLv1;
    private List<String> componentLv2;
    private List<String> problemKr;
    private List<String> problemEn;
    private List<String> opinionKwd;
    private List<String> featureKwd;
    private List<String> senti;
    private List<String> mainTypeName;
    private List<String> modelName;
    private List<String> appName;
    private List<String> osVer;
    private List<String> fwVer;
    private List<String> appVer;
    private List<String> csc;
    private List<String> level3;
    private List<String> vf1;
    private List<String> vf2;
    private List<String> riskId;
    private Long duplicateCount;

    public DocumentInfo(V2VContent v2VContent) {
        this.docId = v2VContent.getDocId();
        this.colctDate = v2VContent.getColctDate();
        this.colctDt = v2VContent.getColctDt();
        this.channelCode = v2VContent.getChannelCode();
        this.title = v2VContent.getTitle();
        this.replaceTitle = v2VContent.getReplaceTitle();
        this.orgTitle = v2VContent.getOrgTitle();
        this.summary = v2VContent.getSummary();
        this.docCn = v2VContent.getDocCn();
        this.orgDocCn = v2VContent.getOrgDocCn();
        this.url = v2VContent.getUrl();
        this.weeks = v2VContent.getWeeks();
        this.langCode = v2VContent.getLangCode();
        this.orgLangCode = v2VContent.getOrgLangCode();
        this.orgLangName = v2VContent.getOrgLangName();
        this.countryName = v2VContent.getCountryName();
        this.siteName = v2VContent.getSiteName();
        this.productCode = v2VContent.getProductCode();
        this.productName = v2VContent.getProductName();
        this.componentLv1 =  v2VContent.getComponentLv1();
        this.componentLv2 = v2VContent.getComponentLv2();
        this.problemKr = v2VContent.getProblemKr();
        this.problemEn = v2VContent.getProblemEn();
        this.opinionKwd = v2VContent.getOpinionKwd();
        this.featureKwd = v2VContent.getFeatureKwd();
        this.senti = v2VContent.getSenti();
        this.mainTypeName = v2VContent.getMainTypeName();
        this.modelName = v2VContent.getModelName();
        this.appName = v2VContent.getAppName();
        this.osVer = v2VContent.getOsVer();
        this.fwVer = v2VContent.getFwVer();
        this.csc = v2VContent.getCsc();
        this.appVer = v2VContent.getAppVer();
        this.level3 = v2VContent.getLevel3();
        this.vf1 = v2VContent.getVf1();
        this.vf2 = v2VContent.getVf2();
        this.riskId = v2VContent.getRiskId();
        this.duplicateCount = v2VContent.getDupicateCount() != null ? v2VContent.getDupicateCount() : null;
    }
}
