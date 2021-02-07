package com.hadsund.spring.test.dto;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/27 2:11 PM
 */
public class TestAA {
    private String content;

    private Integer integer;

    private Long longValue;

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
