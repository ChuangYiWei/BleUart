package com.example.johnny_wei.bleuart.customview;

import android.text.TextUtils;

public class RowItem {

    private String strL1;
    private String strL2;
    private String strL3;


    public RowItem(String strL1, String strL2, String strL3) {
        if (TextUtils.isEmpty(strL1)) {
            strL1 = "Unknow Device";
        }
        this.strL1 = strL1;
        this.strL2 = strL2;
        this.strL3 = strL3;
    }


    public String getstrL2() {
        return strL2;
    }

    public void setstrL2(String strL2) {
        this.strL2 = strL2;
    }

    public String getstrL1() {
        return strL1;
    }

    public void setstrL1(String strL1) {
        this.strL1 = strL1;
    }

    public String getstrL3() {
        return strL3;
    }

    public void setstrL3(String strL3) {
        this.strL3 = strL3;
    }


    @Override
    public String toString() {
        return strL1 + "\n" + strL2 + "\n" + strL3;
    }
}
