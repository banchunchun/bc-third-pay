package com.bc.third.pay.utils;


import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  上午 10:50.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class CustomToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;

    protected void appendDetail(StringBuffer buffer, String fieldName,
                                Object value) {
        if (value instanceof Date) {
            value = new SimpleDateFormat("yyyy-MM-dd").format(value);
        }
        buffer.append(value);
    }
}