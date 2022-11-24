package com.base.util.webhook;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpMethods;
import com.arronlong.httpclientutil.common.HttpResult;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;

/**
 * description  WebHookUtil <BR>
 * <p>
 * author: zhao.song
 * date: created in 13:50  2022/8/24
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class WebHookUtil {

    public static void sendRequest(String url, String method, String json) throws HttpProcessException {
        if (StringUtils.isAnyBlank(url, method, json)) {
            throw new IllegalArgumentException("url、method或消息json为空！");
        }
        HttpConfig config = HttpConfig.custom()
                .timeout(RequestConfig.custom()
                        .setConnectionRequestTimeout(10_000)
                        .setConnectTimeout(5_000)
                        .setSocketTimeout(5_000)
                        .build())
                .json(json)
                .method(HttpMethods.valueOf(method.toUpperCase()))
                .url(url);
        HttpResult httpResult = HttpClientUtil.sendAndGetResp(config);
        if (httpResult.getStatusCode() == 200) {
            final String result = httpResult.getResult();
            System.out.println(result);
        } else {
            throw new IllegalArgumentException("请求失败！返回结果：" + httpResult.getResult());
        }
    }

    public static void main(String[] args) throws HttpProcessException {
        WebHookUtil.sendRequest("http://localhost:5001/demo/hello"
                , "get", "{\"message\":\"我是消息\"}");
    }
}
