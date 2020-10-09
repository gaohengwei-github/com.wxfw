package com.wxfw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * StringHelper
 * 字符串帮助类
 *
 * @author gaohw
 * @date 2020/3/17
 */
public final class StringHelper {

    /**
     * 计算字符串长度（中文2个字符字母1个字符）
     */
    public static int getLength(String str) {
        return str.replaceAll("[^\\x00-\\xff]", "**").length();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    public static String substring(final String str, int start) {
        if (str == null) {
            return null;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }

    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 获取一个UUID字符串
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String zip(String source) {
        ByteArrayOutputStream baos = null;
        GZIPOutputStream gzos = null;
        String result = "";
        try {
            baos = new ByteArrayOutputStream();
            gzos = new GZIPOutputStream(baos);
            gzos.write(source.getBytes());
            result = new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gzos);
            IOUtils.closeQuietly(baos);
        }
        return result;
    }

    public static String unzip(String source) {
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        GZIPInputStream gzis = null;
        String result = "";
        try {
            baos = new ByteArrayOutputStream();
            bais = new ByteArrayInputStream(source.getBytes());
            gzis = new GZIPInputStream(bais);
            byte[] buff = new byte[1024];
            int offset = -1;
            while ((offset = gzis.read(buff)) != -1) {
                baos.write(buff, 0, offset);
            }
            result = new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bais);
            IOUtils.closeQuietly(gzis);
            IOUtils.closeQuietly(baos);
        }
        return result;
    }

    /**
     * 创建数字验证码
     *
     * @param length
     * @return
     */
    public static String createNumCode(int length) {
        StringBuilder str = new StringBuilder(6);
        for (int i = 0; i < length; i++) {
            int val;
            Random random = new Random();
            val = random.nextInt(10);
            str.append(val);
        }
        return str.toString();
    }
}
