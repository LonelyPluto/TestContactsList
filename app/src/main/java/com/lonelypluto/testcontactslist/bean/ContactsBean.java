package com.lonelypluto.testcontactslist.bean;

/**
 * @Description: 通讯录bean
 * @author: ZhangYW
 * @time: 2018/9/12 14:36
 */
public class ContactsBean {

    /**
     * 姓名
     */
    private String name;
    /**
     * 显示拼音的首字母
     */
    private String letters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}
