package com.siti.common.base.vo;

/**
 * Created by hongtu on 2018/6/5.
 * K - V
 */
public class KvVo {
    private String key;
    private String value;

    public KvVo() {
    }

    public KvVo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
