package com.minio.es.common.exception;

public class UnMatchColumnsException extends GlobalServiceException {
    public UnMatchColumnsException(int code, String msg) {
        super(code, msg);
    }
}
