package com.minio.es.vo;

import java.util.List;

/**
 * @author ：yeyh
 * @date ：Created in 2020/9/9 15:22
 * @description：
 * @modified By：
 */
public class UploadFileVo {
    private String filePath;

    private String bucket;

    private String object;

    private List<String> indexPreshuffix;

    private List<String> label;

    public List<String> getIndexPreshuffix() {
        return indexPreshuffix;
    }

    public void setIndexPreshuffix(List<String> indexPreshuffix) {
        this.indexPreshuffix = indexPreshuffix;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
