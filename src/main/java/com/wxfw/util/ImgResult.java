package com.wxfw.util;

/**
 * ImgResult
 *
 * @author gaohw
 * @date 2020/3/18
 */
public class ImgResult {
    private String img;

    private String uuid;

    public ImgResult(String img, String uuid) {
        this.img = img;
        this.uuid = uuid;
    }

    public ImgResult() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
