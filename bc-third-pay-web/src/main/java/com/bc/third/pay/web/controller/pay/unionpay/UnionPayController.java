package com.bc.third.pay.web.controller.pay.unionpay;

import com.bc.third.pay.utils.StackTraceLogUtil;
import com.bc.third.pay.web.controller.BaseController;
import com.google.gson.Gson;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdksample.notice.BackRcvResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-22
 * Time:  下午 5:33.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class UnionPayController extends BaseController {

    private static final String response_fail = "fail";
    private static final String response_success = "success";


    /**
     * 支付回调
     *
     * @throws IOException
     */
    @RequestMapping(value = "/V1/unionpay/callBack.htm")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LogUtil.writeLog("银联支付回调BackRcvResponse接收后台通知开始");
        PrintWriter out = response.getWriter();

        try {
            request.setCharacterEncoding(DEFAULT_CHARSET);
            String encoding = request.getParameter(SDKConstants.param_encoding);
            // 获取请求参数中所有的信息
            Map<String, String> reqParam = BackRcvResponse.getAllRequestParam(request);
            // 打印请求报文
            LogUtil.printRequestLog(reqParam);

            Map<String, String> valideData = null;
            if (null != reqParam && !reqParam.isEmpty()) {
                Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
                valideData = new HashMap<String, String>(reqParam.size());
                while (it.hasNext()) {
                    Map.Entry<String, String> e = it.next();
                    String key = e.getKey();
                    String value = e.getValue();
                    value = new String(value.getBytes(DEFAULT_CHARSET), encoding);
                    valideData.put(key, value);
                }
            }

            if (valideData == null || valideData.size() <= 0) {
                out.println(response_fail);
                return;
            }

            // 获取返回状态
            String respCode = valideData.get("respCode");
            // 订单实付金额
            String money = valideData.get("settleAmt");
            // 银联交易id
            String queryId = valideData.get("queryId");
            // 商户提交的订单批次号
            String batchNo = valideData.get("orderId");
            // certId
            //String certId = valideData.get("certId");
            // 支付时间
            String tradeTime = valideData.get("traceTime");

            Double memberId = 0d;
            @SuppressWarnings("unchecked")
            Map<String, Object> callbackParam = new Gson().fromJson(valideData.get("reqReserved"), Map.class);
            if (callbackParam != null && callbackParam.size() > 0) {
                memberId = (Double) callbackParam.get("memberId");
            }

            logger.info("[paycallback]银联回调   交易状态：{},商户订单号：{}，交易金额：{}分，银联交易号：{},用户：{},支付时间:{}", respCode, batchNo, money, queryId, memberId, tradeTime);

            // 验证签名
            if (!SDKUtil.validate(valideData, encoding)) {
                LogUtil.writeLog("银联支付验证签名结果[失败].");
                logger.error("[paycallback]银联回调   验证签名失败 交易状态：{},商户订单号：{}，交易金额：{}分，银联交易号：{},用户：{}", respCode, batchNo, money, queryId, memberId);
                out.println(response_fail);
                return;
            }

            LogUtil.writeLog("银联支付验证签名结果[成功].");
            LogUtil.writeLog("银联支付回调valideData--->" + valideData.toString());

            if (!"00".equals(respCode)) {
                logger.error("[paycallback]银联回调  支付失败  交易状态：{},商户订单号：{}，交易金额：{}分，银联交易号：{},用户：{}", respCode, batchNo, money, queryId, memberId);
                out.println(response_fail);
                return;
            }

            try {
                // 验证订单
//                boolean isSuccess = orderSettlementService.processPaySuccessCallback(OrderPayType.UNIONPAY,batchNo,money, "银联支付："+memberId+"",queryId,respCode);
//                if (!isSuccess){
//                    out.println(response_fail);
//                    return;
//                }
                logger.info("[paycallback]银联回调  支付成功     交易状态：{},商户订单号：{}，交易金额：{}分，银联交易号：{},用户：{}", respCode, batchNo, money, queryId, memberId);
                out.println(response_success);

            } catch (Exception e) {
                logger.error("[paycallback]银联回调  支付成功   执行方法失败 交易状态：{},商户订单号：{}，交易金额：{}分，银联交易号：{},用户：{},msg={}", respCode, batchNo, money, queryId, memberId, StackTraceLogUtil.getStackTraceAsString(e));
                out.println(response_fail);
            }
        } catch (Exception e) {
            out.println(response_fail);
            logger.error("[paycallback]银联回调异常  msg={}", StackTraceLogUtil.getStackTraceAsString(e));
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    }
}
