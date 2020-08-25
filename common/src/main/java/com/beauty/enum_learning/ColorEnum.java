package com.beauty.enum_learning;

import java.awt.*;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/27 10:04
 * @Version: 1.0
 */
public enum ColorEnum implements Behaviour {
    RED("红色","#10034"){
        @Override
        public String op() {
            return RED.colorName+"-"+RED.colorCode;
        }
    },GREEN("绿色","#23412"){
        @Override
        public String op() {
            return GREEN.name();
        }
    };

    private String colorName;
    private String colorCode;


    private ColorEnum(String colorName, String colorCode) {
        this.colorName = colorName;
        this.colorCode = colorCode;

    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
