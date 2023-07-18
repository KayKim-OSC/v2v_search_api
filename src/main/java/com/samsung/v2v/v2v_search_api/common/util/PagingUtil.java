package com.samsung.v2v.v2v_search_api.common.util;

public class PagingUtil {

    public static int calculateSearchFrom(int pageIndex, int pageSize) {
        return (pageIndex - 1) * pageSize;
    }
}
