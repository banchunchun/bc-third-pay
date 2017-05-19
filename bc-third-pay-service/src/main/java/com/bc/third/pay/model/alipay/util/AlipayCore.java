package com.bc.third.pay.model.alipay.util;

import com.bc.third.pay.model.alipay.config.AlipayConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.PartSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 */

public class AlipayCore {

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createStringForSyncNotify(Map<String, String> params) {

        // 签约合作者身份ID
        String orderInfo = "partner=".concat(params.get("partner"));
        // 签约卖家支付宝账号
        orderInfo += "&seller_id=".concat(params.get("seller_id"));
        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=".concat(params.get("out_trade_no"));
        // 商品名称
        orderInfo += "&subject=".concat(params.get("subject"));
        // 商品详情
        orderInfo += "&body=".concat(params.get("body"));
        // 商品金额
        orderInfo += "&total_fee=".concat(params.get("total_fee"));
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=".concat(params.get("notify_url"));
        // 服务接口名称， 固定值
        orderInfo += "&service=".concat(params.get("service"));
        // 支付类型， 固定值
        orderInfo += "&payment_type=".concat(params.get("payment_type"));
        // 参数编码， 固定值
        orderInfo += "&_input_charset=".concat(params.get("_input_charset"));
        orderInfo += "&it_b_pay=".concat(params.get("it_b_pay"));
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=".concat(params.get("return_url"));
        orderInfo += "&success=".concat(params.get("success"));

        return orderInfo;
    }

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(AlipayConfig.log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成文件摘要
     * @param strFilePath 文件路径
     * @param file_digest_type 摘要算法
     * @return 文件摘要结果
     */
    public static String getAbstract(String strFilePath, String file_digest_type) throws IOException {
        PartSource file = new FilePartSource(new File(strFilePath));
        if(file_digest_type.equals("MD5")){
            return DigestUtils.md5Hex(file.createInputStream());
        }
        else if(file_digest_type.equals("SHA")) {
            return DigestUtils.sha256Hex(file.createInputStream());
        }
        else {
            return "";
        }
    }
}
