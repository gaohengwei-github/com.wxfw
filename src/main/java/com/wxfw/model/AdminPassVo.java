package com.wxfw.model;

import springfox.documentation.annotations.ApiIgnore;

/**
 * AdminPassVo
 *
 * @author gaohw
 * @date 2020/4/4
 */
@ApiIgnore
public class AdminPassVo {

    private String oldPass;

    private String newPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
