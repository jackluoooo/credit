package info.bicoin.credit.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class OkhttpUtil {
    public static JSONObject doPost(JSONObject requestJson, String url, String token) throws Exception{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(requestJson));
        //创建一个请求对象
        JSONObject jsonObject =new JSONObject();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).addHeader("token",token)
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            log.info("execute.isSuccessful():{}",execute.isSuccessful());
            if(execute.isSuccessful()){
                String body=execute.body().string();
                 jsonObject= JSONObject.parseObject(body);
            }
        } catch (IOException e) {
            throw e;
        }
        return jsonObject;
    }

    public static JSONObject doGet( String url, String token) throws Exception{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //创建一个请求对象
        JSONObject jsonObject =new JSONObject();
        Request request = new Request.Builder()
                .url(url)
                .get().addHeader("token",token)
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()){
                String body=execute.body().string();
                jsonObject= JSONObject.parseObject(body);
            }
        } catch (IOException e) {
            throw e;
        }
        return jsonObject;
    }

}
;