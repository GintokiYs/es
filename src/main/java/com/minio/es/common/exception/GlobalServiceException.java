package com.minio.es.common.exception;


import com.minio.es.common.status.ResponseStatus;

public class GlobalServiceException extends RuntimeException {

    private int code;//异常码

    /**
     * @param responseStatus 响应状态载体
     */
    public GlobalServiceException(ResponseStatus responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.value();
    }

    public GlobalServiceException(ResponseStatus.DataSource responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.Development responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.Project responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.Publisher responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.Usermanage responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.MetaDataQuatity responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(ResponseStatus.RealTimeTask responseStatus) {
        super(responseStatus.getReasonPhrase());
        this.code = responseStatus.getValue();
    }

    public GlobalServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
