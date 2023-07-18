package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LanguageCode {

    LANG_CODE_CHINESS("12"),
    LANG_CODE_ENGLISH("19"),
    LANG_CODE_FRENCH("22"),
    LANG_CODE_GERMAN("23"),
    LANG_CODE_ITALIAN("34"),
    LANG_CODE_JAPANESS("35"),
    LANG_CODE_KOREAN("36"),
    LANG_CODE_PORTUGUESE("49"),
    LANG_CODE_RUSSIAN("51"),
    LANG_CODE_SPANISH("57");
    private final String value;

    public static LanguageCode valueOfFileName(String fileName){
        for(LanguageCode languageCode : LanguageCode.values()){
            /*ss_voc_contents_template_{langCode}  -- synonym 미적용 버전*/
            String defaultTeamplateName = Prefix.INDEX_TEMPLATE_NAME_PREFIX.getValue() + languageCode.value;
            /*ss_voc_contents_template_synonym_{langCode} -- synonym 적용버전*/
            String synonymTemplateName = Prefix.INDEX_TEMPLATE_NAME_PREFIX.getValue() + languageCode.getValue()+"_"+Prefix.SYNONYM.getValue();
            if(defaultTeamplateName.equals(fileName)){
                return languageCode;
            }else if (synonymTemplateName.equals(fileName)){
                return languageCode;
            }
        }
        throw new NullPointerException("등록된 Language Code가 없습니다. fileName에 해당하는 Language 코드를 정의해주세요. "+fileName);
    }
}

