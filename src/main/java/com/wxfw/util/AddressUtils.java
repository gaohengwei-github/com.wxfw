package com.wxfw.util;

import com.wxfw.util.json.JSON;
import com.wxfw.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AddressUtils
 *
 * @author gaohw
 * @date 2020/4/2
 */
public class AddressUtils {
    public static final String IP_URL = "http://region.zmrit.com";
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StrUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj;
        try {
            obj = JSON.unmarshal(rspStr, JSONObject.class);
            JSONObject data = obj.getObj("data");
            address = data.getStr("address");
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
