package com.minio.es.vo;

import java.util.List;

/**
 * @author ：yeyh
 * @date ：Created in 2020/9/10 8:52
 * @description：
 * @modified By：
 */
public class SearchVo {
    private String type;

    private String keyword;

    private List<String> labels;

    private int pageSize;

    private int page;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
