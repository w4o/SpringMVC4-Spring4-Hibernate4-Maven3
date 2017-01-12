package com.ueedit.common.utils.ajax;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Frank on 2017/1/12.
 */
public class AjaxResponse {

    @JSONField(name="code")
    private Integer code;
    @JSONField(name="msg")
    private String message;
    @JSONField(name="res")
    private Object res;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }
}
