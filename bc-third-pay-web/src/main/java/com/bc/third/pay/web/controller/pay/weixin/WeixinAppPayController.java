package com.bc.third.pay.web.controller.pay.weixin;

import com.bc.third.pay.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-22
 * Time:  上午 11:58.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class WeixinAppPayController extends BaseController{
    /**
     * APP端调起支付需要先向服务端发起请求，获取prepayId
     *
     * @author banchun
     * @date 2015年8月10日 下午5:01:37
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = {"/V1/wxpay/clientSignSubmitOrderInfo.htm"})
    public void clientSignSubmitOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderNo) throws IOException {


    }


}
