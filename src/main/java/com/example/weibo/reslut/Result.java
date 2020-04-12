package com.example.weibo.reslut;

/**
 * @program: wespirder-charterone
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-11 16:54
 */
public class Result {
    //响应码
    private int code;

    public Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
