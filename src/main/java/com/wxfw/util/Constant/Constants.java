package com.wxfw.util.Constant;

import java.math.BigDecimal;

/**
 * @program: hjmall
 * @description 实体类的常量定义
 * @author: Gaohw
 * @create: 2020-03-17 22:07
 **/
public interface Constants {


    String SPACE = " ";
    BigDecimal ZERO = new BigDecimal(0);
    BigDecimal ONE_HUNDRED = new BigDecimal(100);

    String REQ_PAGESIZE = "ps";
    String REQ_CUR_PAGE = "cp";

    interface OAUTH {
        String HEADER_KEY_NAME = "Authorization";
    }

    interface TOKEN {
        String HEADER_KEY_NAME = "Authorization";
        String JWT_SECURITY_KEY = "yneeServicewxfw";
        String TMP_COOKIE_NAME = "tmp_token";
    }


}
