package com.bc.third.pay.web.controller;

import com.bc.third.pay.web.response.ResponseProcessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  下午 3:46.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public abstract class BaseController extends ResponseProcessor{

    protected final String response_success = "success";

    protected final String response_fail = "fail";

    /**
     * 获取支付宝返回参数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    protected Map<String, String> parseAlipayReturnParams(
            HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
