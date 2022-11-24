package com.beauty;



/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/11/17 16:33
 * @version: 1.0
 ***********************/
public class DocPlatformHelper {

    public enum DocPlatformTypeEnum {

        /*--start- 通用渠道--*/
        DAIBIAN("待编"),
        DAISHEN("待审"),
        YIQIANFA("已签发"),
        /*--end--*/

        /*--start- 纸媒渠道--*/
        DAIYONG("待用稿"),
        JINRI("今日稿"),
        SHANGBAN("上版稿"),
        YIQIAN("已签稿"),
        GUIDANG("归档稿")
        /*--end--*/;

        private String platformName;

        DocPlatformTypeEnum(String platformName) {
            this.platformName = platformName;
        }

        public String getPlatformName() {
            return platformName;
        }
    }

    public static void main(String[] args) {

    }
}
