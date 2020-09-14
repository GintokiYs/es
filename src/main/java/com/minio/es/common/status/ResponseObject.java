package com.minio.es.common.status;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseObject extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;


    public ResponseObject() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseObject error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static ResponseObject error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static ResponseObject error(int code, String msg) {
        ResponseObject r = new ResponseObject();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResponseObject ok(String msg) {
        ResponseObject r = new ResponseObject();
        r.put("msg", msg);
        return r;
    }

    public static ResponseObject ok(Map<String, Object> map) {
        ResponseObject r = new ResponseObject();
        r.putAll(map);
        return r;
    }

    public static ResponseObject ok() {
        return new ResponseObject();
    }

    public static ResponseObject ok(Object value) {
        ResponseObject r = new ResponseObject();
        r.put("data", value);
        return r;
    }

    public static ResponseObject ok(ResponseStatus responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.value());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.value());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus.Development responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.getValue());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus.DataSource responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.getValue());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus.Usermanage responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.getValue());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus.Project responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.getValue());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject error(ResponseStatus.Publisher responseStatus) {
        ResponseObject r = new ResponseObject();
        r.put("code", responseStatus.getValue());
        r.put("msg", responseStatus.getReasonPhrase());
        return r;
    }

    public static ResponseObject apiError(String msg) {

        return error(1, msg);
    }

    @Override
    public ResponseObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
