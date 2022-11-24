//package com.base.helper;
//
//import com.trs.common.utils.StringUtils;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @Author:zhao.song
// * @Date:2020/1/18 19:25
// * @Version:1.0
// */
//public class HttpClientHelper {
//
//
//    public String doPostByHeader(String url, Map<String, Object> hparams) throws IOException {
//        HttpPost httpPost = new HttpPost(url);
//        //装填参数
//        List<NameValuePair> nvps = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : hparams.entrySet()) {
//            nvps.add(new BasicNameValuePair(entry.getKey(), StringUtils.toStringValue(entry.getValue())));
//        }
//        //设置参数到请求对象中
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        httpPost.setHeader("token"
//                ,"AAAABQAMdGVzdEB0cnMuY29tACl0ZXN0QHRycy5jb20sMywxNTc5MzQwNTIwMzQ3LDUsbnVsbCxhZG1pbgAAAC8wLQIVAJRBgPDeyyipCZUbUndvJoEetdCwAhQFHBBDRElPrHQnt8t0lG08CUER9g..");
//        CloseableHttpResponse execute = HttpClients.createDefault().execute(httpPost);
//        return EntityUtils.toString(execute.getEntity());
//    }
//
//    public static void main(String[] args) {
//        Map<String, Object> hparams = new HashMap<>();
//
//        hparams.put("serviceid","");
//        hparams.put("methodname","");
//        hparams.put("CurrPage","");
//        hparams.put("PageSize","");
//        hparams.put("PersonalMaterialId","");
//        hparams.put("fileType","");
//        hparams.put("order","");
//        hparams.put("searchValue","");
//        hparams.put("status","");
//    }
//}
