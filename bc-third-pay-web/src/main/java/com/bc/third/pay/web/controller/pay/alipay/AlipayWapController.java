package com.bc.third.pay.web.controller.pay.alipay;

import com.bc.third.pay.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付宝 h5 提交支付信息 、异步通知信息
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-24
 * Time:  上午 11:16.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class AlipayWapController extends BaseController{



    /**
     * 提交支付宝信息
     *
     * @param request
     * @param response
     * @param batchNo
     * @param payPrice
     */
    @RequestMapping("/V1/alipay/wap/clientSignSubmitOrderInfo.htm")
    public void clientSignSubmitOrderInfo(HttpServletRequest request, HttpServletResponse response, String batchNo, Integer payPrice) {

    }

}
