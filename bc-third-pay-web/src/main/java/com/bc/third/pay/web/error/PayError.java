package com.bc.third.pay.web.error;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  下午 3:44.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public enum PayError {
    /*  系统内部错误   代码： 负数开头    */
    SYS_AUTH_FAIL(-1,"账户验证失败，请重新登陆"),
    SYS_ILEGAL_SIGNATURE(-2, "签名错误"),
    SYS_ILEGAL_REQUEST(-3, "非法请求"),
    SYS_FREQ_LIMITED(-4, "达到频率限制上限"),
    SYS_PARAM_NULL(-5, "系统必要参数为空"),
    SYS_PARAM_ERROR(-6, "系统必要参数错误"),
    SYS_EXCEPTION_SERVER(-7, "系统异常");

    private PayError(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private int code;
    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
