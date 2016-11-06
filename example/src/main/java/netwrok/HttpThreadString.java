package netwrok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import value.App;

/**
 * Created by oreo on 2016/10/14.
 */
public class HttpThreadString extends Thread{

    private Map map;
    private SharedPreferences sh;
    private String url;
    private Handler handler;
    private Context context;
    public String returnString;
    public  ProgressDialog proDialog;

    public HttpThreadString(Handler handler, Context context, Map map, ProgressDialog proDialog) {
        if(handler!=null)
        this.handler = handler;
        this.context = context;
        this.map = map;
        this.url=(App.url+"/VServer/"+map.get("method"));
        this.proDialog=proDialog;
        Log.i("HttpStart","url");
    }

    public JSONObject createJSON(){
        JSONObject result = new JSONObject();
        try {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                result.put(""+entry.getKey() , ""+entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void run()
    {
        if(isInterrupted())
        {
            return;
        }
        try{
            URL httpUrl=new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            JSONObject jsonData =createJSON();
            String result=String.valueOf(jsonData);
            System.out.println(result);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");


            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(result.getBytes());
            outputStream.close();

            Log.i("response", connection.getResponseCode() + "");
            if(connection.getResponseCode()==200){
                Log.i("response",connection.getResponseCode()+"");
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader
                        (connection.getInputStream())) ;
                String line=null;
                final StringBuffer stringBuffer=new StringBuffer();
                while((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line);
                }

                map.put("return", "" + stringBuffer.toString());

                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("state", "" + stringBuffer.toString());
                msg.setData(b);
                if(handler!=null)
                handler.sendMessage(msg);
            }
            else{
                Log.i("logintest",""+"http_fail");
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("state", "失败" + connection.getResponseCode());
                msg.setData(b);
                if(handler!=null)
                handler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
