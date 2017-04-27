package top.guyi.assistant;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 古逸 on 2017-04-27.
 */
public class TulingRequest {

    public static JSONObject api(String info){

        Map<String,String> params = new HashMap<String,String>();
        params.put("key","<图铃机器人key>");
        params.put("info",info);
        params.put("loc","重庆市渝北区");
        String result = post("http://www.tuling123.com/openapi/api",params);
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String post(String url,Map<String,String> params){

        HttpPost client = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> e : params.entrySet()){
            nameValuePairs.add(new BasicNameValuePair(e.getKey(),e.getValue()));
        }

        String result = null;
        try {
            // 设置httpPost请求参数
            client.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(client);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
