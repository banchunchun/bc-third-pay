package com.bc.third.pay.service;

import java.io.UnsupportedEncodingException;

/**
 * 提交订单支付信息到第三方支付系统
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  下午 2:22.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public interface PayService {

    /**
     * 提交支付宝APP支付信息
     *
     * @param batchNo  商户订单号
     * @param payPrice 支付价格
     *                 <pre>
     *                                 （此方法不能被单独调用），只能通过内部微服务方式提供，
     *                                 <b>本案例仅作示例，未做此方面校验，正式项目请慎用，禁止传输价格</b>
     *                                 1.尽量是由订单服务提供查询金额的方法
     *                                 2.或者才用混淆加密
     *                                 </pre>
     * @return 生成加密数据信息
     */
    String submitAlipayAppInfo(String batchNo, int payPrice) throws UnsupportedEncodingException;


    /**
     * 获取银联支付tn号
     *
     * @param batchNo     订单号
     * @param reqReserved 透传区域，银联异步通知会原封返回 格式为 json {}
     * @return
     */
    String getTn(String batchNo, String reqReserved);
}
