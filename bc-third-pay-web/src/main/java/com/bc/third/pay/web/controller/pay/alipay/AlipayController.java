package com.bc.third.pay.web.controller.pay.alipay;

import com.bc.third.pay.model.alipay.config.AlipayConfig;
import com.bc.third.pay.model.alipay.util.AlipayNotify;
import com.bc.third.pay.service.PayService;
import com.bc.third.pay.utils.StackTraceLogUtil;
import com.bc.third.pay.web.controller.BaseController;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  下午 2:19.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class AlipayController extends BaseController {

    @Autowired
    private PayService payService;

    /**
     * 提交支付宝信息
     *
     * @param request
     * @param response
     * @param batchNo
     * @param payPrice
     */
    @RequestMapping("/V1/alipay/clientSignSubmitOrderInfo.htm")
    public void clientSignSubmitOrderInfo(HttpServletRequest request, HttpServletResponse response, String batchNo, Integer payPrice) {

        try {
            String orderInfo = payService.submitAlipayAppInfo(batchNo, payPrice);
            JsonObject jo = new JsonObject();
            jo.addProperty("data", orderInfo);
            responseSuccessJson(response, jo.toString());
        } catch (UnsupportedEncodingException e) {
            responseSuccessJson(response, "");
        }
    }

    /**
     * 妈妈好V1.1版本
     * 支付宝交易完成异步通知
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/V1/alipay/callback.htm")
    public void alipayNotifyUrl2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = parseAlipayReturnParams(request);

        if (params == null || params.size() <= 0) {
            out.println(response_fail);
            return;
        }

        // 商户订单号
        String out_trade_no = params.get("out_trade_no");
        // 支付宝交易号
        String trade_no = params.get("trade_no");
        // 交易状态
        String trade_status = params.get("trade_status");
        // 支付账户
        String buyer_email = params.get("buyer_email");
        // 订单金额
        String price = params.get("total_fee");
        try {
            logger.info("[paycallback]支付宝V1.1回调      商户订单号：{}，支付宝交易号：{}，交易状态：{}，支付账户：{},订单金额：{}", out_trade_no, trade_no, trade_status, buyer_email, price);

            // 验证失败
            if (!AlipayNotify.verify(params, AlipayConfig.rsa_sign_type, true)) {
                logger.error("[paycallback]支付宝V1.1回调  验证失败     商户订单号：{}，支付宝交易号：{}，交易状态：{}，支付账户：{},订单金额：{}", out_trade_no, trade_no, trade_status, buyer_email, price);
                out.println(response_fail);
                return;
            }
            // 交易成功
            if (trade_status.equals("TRADE_FINISHED")) {
                out.println(response_success);
                return;
            }
            // 交易创建
            else if (trade_status.equals("WAIT_BUYER_PAY")) {
                out.println(response_success);
                return;
            }
            // 交易关闭
            else if (trade_status.equals("TRADE_CLOSED")) {
                out.println(response_success);
                return;
            }
            // 支付成功
            else if (trade_status.equals("TRADE_SUCCESS")) {
                logger.info("[paycallback]支付宝V1.1回调   支付成功     商户订单号：{}，支付宝交易号：{}，交易状态：{}，支付账户：{},订单金额：{}", out_trade_no, trade_no, trade_status, buyer_email, price);
                out.println(response_success);
            } else {
                logger.error("[paycallback]支付宝V1.1回调  错误code    商户订单号：{}，支付宝交易号：{}，交易状态：{}，支付账户：{},订单金额：{}", out_trade_no, trade_no, trade_status, buyer_email, price);
                out.println(response_fail);
                return;
            }
        } catch (Exception ex) {
            logger.error("[paycallback]支付宝V1.1回调    Exception 商户订单号：{}，支付宝交易号：{}，交易状态：{}，支付账户：{},订单金额：{},msg={}", out_trade_no, trade_no, trade_status, buyer_email, price, StackTraceLogUtil.getStackTraceAsString(ex));
            out.println(response_fail);
        }
    }
}
