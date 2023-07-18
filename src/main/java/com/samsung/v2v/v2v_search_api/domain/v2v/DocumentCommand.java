package com.samsung.v2v.v2v_search_api.domain.v2v;

import com.samsung.v2v.v2v_search_api.common.constants.Criteria;
import com.samsung.v2v.v2v_search_api.common.constants.LanguageCode;
import com.samsung.v2v.v2v_search_api.common.constants.QueryOption;
import com.samsung.v2v.v2v_search_api.common.exception.InvalidParamException;
import com.samsung.v2v.v2v_search_api.common.util.ConverterUtil;
import com.samsung.v2v.v2v_search_api.common.util.IndexGenerator;
import com.samsung.v2v.v2v_search_api.common.util.PagingUtil;
import com.samsung.v2v.v2v_search_api.common.util.RequestDataValidator;
import com.samsung.v2v.v2v_search_api.interfaces.v2v.SearchDto;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;


public class DocumentCommand {

    @Getter
    @ToString
    public static class RegisterRequest{
        private List<String> targetIndexList;
        private LocalDate sDate;
        private LocalDate eDate;
        private String orgLangCode;
        private String oderBy=QueryOption.ORDERBY_COLCT_DT.getValue();
        private String duplicateYn=QueryOption.DISABLE_AGGREGATION.getValue();
        private int searchFrom;
        private int searchSize;
        private String keyword;
        private List<String> types;
        private List<String> productCodes;
        private List<String> channelCodes;
        private List<String> componentCodes;
        private List<String> opinionCodes;
        private List<String> siteCodes;

        private List<String> itemCodes;
        private List<String> gbms;
        private List<String> makers;
        private List<String> mainTypes;
        private List<String> osVers;
        private List<String> fwVers;
        private List<String> appCodes;
        private List<SearchDto.AddKeywordInfo> addKeywords;

        private String synonymYn=QueryOption.DISABLE_SYNOYM.getValue();
        public RegisterRequest(SearchDto.RegisterRequest request) {
            if (StringUtils.isEmpty(request.getOrgLangCode())) {
                this.orgLangCode = LanguageCode.LANG_CODE_KOREAN.getValue();
            } else {
                this.orgLangCode = request.getOrgLangCode();
            }
            this.sDate = RequestDataValidator.validateExistsIndexDate(ConverterUtil.convertStringtoLocalDate(request.getSDate()));
            if (LocalDate.now().plusDays(1).isAfter(ConverterUtil.convertStringtoLocalDate(request.getEDate()))) {
                this.eDate = RequestDataValidator.validateExistsIndexDate(ConverterUtil.convertStringtoLocalDate(request.getEDate()));
            }else{
                throw new InvalidParamException("eDate must be before thre present");
            }
            this.oderBy = request.getOrderBy();
            this.duplicateYn = request.getDuplicateYn();
            this.searchSize = request.getPageSize() ==0 ? Criteria.DEFAULT_PAGING_SIZE_COUNT : request.getPageSize();
            this.searchFrom = PagingUtil.calculateSearchFrom(request.getPageIndex(), this.searchSize);
            this.keyword = StringUtils.isEmpty(request.getKeyword()) ? null : request.getKeyword();
            this.types = RequestDataValidator.validateInitializeData(request.getTypes());
            this.productCodes = RequestDataValidator.validateInitializeData(request.getProductCodes());
            this.channelCodes = RequestDataValidator.validateInitializeData(request.getChannelCodes());
            this.componentCodes = RequestDataValidator.validateInitializeData(request.getComponentCodes());
            this.opinionCodes = RequestDataValidator.validateInitializeData(request.getOpinionCodes());
            this.siteCodes = RequestDataValidator.validateInitializeData(request.getSiteCodes());
            this.itemCodes = RequestDataValidator.validateInitializeData(request.getItemCodes());
            this.gbms=RequestDataValidator.validateInitializeData(request.getGbms());
            this.makers=RequestDataValidator.validateInitializeData(request.getMakers());
            this.mainTypes=RequestDataValidator.validateInitializeData(request.getMainTypes());
            this.osVers=RequestDataValidator.validateInitializeData(request.getOsVers());
            this.fwVers=RequestDataValidator.validateInitializeData(request.getFwVers());
            this.appCodes=RequestDataValidator.validateInitializeData(request.getAppCodes());
            this.addKeywords = RequestDataValidator.validateInitializeAddKeyword(request.getAddKeyWords());
            this.synonymYn= request.getSynonymYn();
            this.targetIndexList = IndexGenerator.createTargetIndexs(this.synonymYn,this.getSDate(), this.getEDate(), this.getOrgLangCode());
        }
    }
    @Getter
    @ToString
    public static class RegisterReplaceTitleRequest{
        private List<String> targetIndexList;
        private LocalDate sDate;
        private LocalDate eDate;
        private String orgLangCode;
        private String replaceTitle;
        private int searchFrom;
        private int searchSize;
        private String synonymYn=QueryOption.DISABLE_SYNOYM.getValue();
        public RegisterReplaceTitleRequest(SearchDto.RegisterReplaceTitleRequest request) {

            this.sDate = RequestDataValidator.validateExistsIndexDate(ConverterUtil.convertStringtoLocalDate(request.getSDate()));
            if (LocalDate.now().plusDays(1).isAfter(ConverterUtil.convertStringtoLocalDate(request.getEDate()))) {
                this.eDate = RequestDataValidator.validateExistsIndexDate(ConverterUtil.convertStringtoLocalDate(request.getEDate()));
            }else{
                throw new InvalidParamException("eDate must be before thre present");
            }
            this.replaceTitle = StringUtils.isEmpty(request.getReplaceTitle()) ? null : request.getReplaceTitle();
            this.orgLangCode = request.getOrgLangCode();
            this.searchSize = request.getPageSize() ==0 ? Criteria.DEFAULT_PAGING_SIZE_COUNT : request.getPageSize();
            this.searchFrom = PagingUtil.calculateSearchFrom(request.getPageIndex(), this.searchSize);
            this.targetIndexList = IndexGenerator.createTargetIndexs(this.synonymYn,this.getSDate(), this.getEDate(), this.getOrgLangCode());

        }
    }






}
