package com.base.util.workwx;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.HttpMethods;
import com.arronlong.httpclientutil.common.HttpResult;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;

import java.util.*;

/**
 * description  WorkWXUtil <BR>
 * <p>
 * author: zhao.song
 * date: created in 15:56  2022/8/11
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class WorkWXUtil {

    private static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/";

    private static final String ACCESS_TOKEN_POSTFIX = "gettoken?corpid=%s&corpsecret=%s";

    private static final String MESSAGE_SEND_POSTFIX = "message/send?access_token=%s";

    public static Optional<AccessTokenInfo> getAccessToken(String corpId, String corpSecret) {
        if (StringUtils.isBlank(corpId) || StringUtils.isBlank(corpSecret)) {
            // TODO 日志
            return Optional.empty();
        }
        Optional<AccessTokenInfo> optional = Optional.empty();
        try {
            HttpConfig config = HttpConfig.custom()
                    .timeout(RequestConfig.custom()
                            .setConnectionRequestTimeout(10_000)
                            .setConnectTimeout(5_000)
                            .setSocketTimeout(5_000)
                            .build())
                    .method(HttpMethods.GET)
                    .url(generateAccessTokenUrl(corpId, corpSecret));
            HttpResult httpResult = HttpClientUtil.sendAndGetResp(config);
            if (httpResult.getStatusCode() != 200) {
                throw new RuntimeException("请求发生错误！" + httpResult.getResult());
            }
            AccessTokenInfo accessTokenInfo = new ObjectMapper().readValue(httpResult.getResult(), AccessTokenInfo.class);
            if (Objects.nonNull(accessTokenInfo) && accessTokenInfo.getErrCode() == 0) {
                accessTokenInfo.setLastRefreshTime(new Date());
                optional = Optional.of(accessTokenInfo);
            }
            if (Objects.nonNull(accessTokenInfo) && accessTokenInfo.getErrCode() != 0) {
                throw new RuntimeException(accessTokenInfo.getErrMsg());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return optional;

    }

    public static boolean sendTextType(String accessToken, WXMessageInfo messageInfo) {
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        boolean isSuccess = false;
        try {
            String url = generateSendMessageUrl(accessToken);
            String reqParams = new ObjectMapper().writeValueAsString(messageInfo);
            Header[] headers = HttpHeader.custom()
                    .contentType("application/json").build();
            HttpConfig config = HttpConfig.custom()
                    .timeout(RequestConfig.custom()
                            // ms
                            .setConnectionRequestTimeout(10_000)
                            .setConnectTimeout(10_000)
                            .setSocketTimeout(10_000)
                            .build())
                    .headers(headers).url(url).json(reqParams);
            String result = HttpClientUtil.post(config);
            WXMessageResponseInfo resp = new ObjectMapper().readValue(result, WXMessageResponseInfo.class);
            if (Objects.nonNull(resp) && resp.getErrCode() == 0) {
                isSuccess = true;
            }
            if (Objects.nonNull(resp) && resp.getErrCode() != 0) {
                throw new RuntimeException(resp.getErrMsg());
            }
            System.out.println(resp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return isSuccess;

    }

    private static String generateAccessTokenUrl(String corpId, String corpSecret) {
        return String.format(BASE_URL.concat(ACCESS_TOKEN_POSTFIX), corpId, corpSecret);
    }

    private static String generateSendMessageUrl(String accessToken) {
        return String.format(BASE_URL.concat(MESSAGE_SEND_POSTFIX), accessToken);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AccessTokenInfo {
        @JsonAlias("errcode")
        private int errCode;

        @JsonAlias("errmsg")
        private String errMsg;

        @JsonAlias("access_token")
        private String accessToken;

        @JsonAlias("expires_in")
        private int expireTime;

        private Date lastRefreshTime;

        public boolean isExpired() {
            int duration = (int) ((System.currentTimeMillis() - lastRefreshTime.getTime()) / 1000);
            // 运行60秒误差
            return (duration + 60) > expireTime;
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class WXMessageInfo {
        @JsonProperty("touser")
        private String toUser;

        @JsonProperty("msgtype")
        private String msgType;

        @JsonProperty("agentid")
        private String agentId;

        private Map<String, String> text;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class WXMessageResponseInfo {
        @JsonAlias("errcode")
        private int errCode;

        @JsonAlias("errmsg")
        private String errMsg;

        @JsonAlias("msgid")
        private String msgId;

        @JsonAlias("invaliduser")
        private String invalidUser;

        @JsonAlias("invalidparty")
        private String invalidParty;

        @JsonAlias("invalidtag")
        private String invalidTag;
    }

    public static void main(String[] args) throws JsonProcessingException {
//        String jsonStr = "{\"access_token\":\"21212\",\"expires_in\":\"7200\"}";
//        AccessTokenInfo tokenInfo = new ObjectMapper().readValue(jsonStr, AccessTokenInfo.class);
//        System.out.println(tokenInfo);
//        System.out.println(Optional.of("正确了").orElseThrow(() -> new RuntimeException("错误了")));
        System.out.println(getAccessToken("wwed5a9e468d8e95c3", "swVmrKDUdyOApTQALQTPn67wYHGbkAFl318giQxe6oU"));


    }

    private static void sendTextType() throws JsonProcessingException {
        WXMessageInfo messageInfo = WXMessageInfo.builder()
                .toUser("ZhaoSong2")
                .msgType("text")
                .agentId("1000002")
                .text(new HashMap<String, String>() {
                    {
                        put("content", "测试信息");
                    }
                })
                .build();

        System.out.println(new ObjectMapper().writeValueAsString(messageInfo));
        sendTextType("1lJgPCyxa-Bb_Aj3ZThY6fpPM3HNzDhpYOKnQzxKvn_VTKkV6nmd3jv0deJVyzb2HB92BypxxLiFDsF_BjpRmCbJ9q8HueS0gPBoG8Ctneuicx3tGC69ezhY-zPrkNZstp8T5vrpTscDDxsfJMjyeaVqanbzEATV13TkJYXRyXq5LXv2n7weLXrne1F_m4MALfpMmeN-MqOiiZhPha2Orw"
                , messageInfo);
    }

}
