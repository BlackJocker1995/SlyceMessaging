package it.snipsnap.slyce_messaging_example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;
import netwrok.HttpThreadString;
import value.MyMessage;

public class SendActivity extends AppCompatActivity {
    private Intent intent;
    private int sendid;
    private int recid;
    private String sendicon;
    private String recicon;
    private SharedPreferences sp;

    private Map getmap;
    private Map sendmap;
    private HttpThreadString httpThreadString;

    private volatile static int n = 0;
    private Handler getMessHandler;
    ScheduledExecutorService scheduleTaskExecutor;

    SlyceMessagingFragment slyceMessagingFragment;

    private boolean hasLoadedMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(it.snipsnap.slyce_messaging_example.R.layout.activity_send);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        intent = getIntent();
        this.recid = intent.getIntExtra("personid", 0);
        this.sendid = sp.getInt("user_id", -1);
        this.recicon = intent.getStringExtra("icon");
        this.sendicon = sp.getString("user_icon", "");
        hasLoadedMore = false;

        CreateMap();
        CreateHandler();

        //默认Url
        slyceMessagingFragment = (SlyceMessagingFragment) getFragmentManager().findFragmentById(R.id.fragment_for_slyce_messaging);
        slyceMessagingFragment.setDefaultAvatarUrl(this.sendicon);//头像
        slyceMessagingFragment.setDefaultDisplayName("user");//显示默认名字
        slyceMessagingFragment.setDefaultUserId(sp.getString("user_name", ""));//userid

        slyceMessagingFragment.setOnSendMessageListener(new UserSendsMessageListener() {
            @Override
            public void onUserSendsTextMessage(String text) {
                //发送信息
                Log.d("inf", "******************************** " + text);
                sendmap.put("content", text);
                HttpThreadString send = new HttpThreadString(null, SendActivity.this, sendmap, null);
                send.start();
                // sendMessage(text);
            }

            @Override
            public void onUserSendsMediaMessage(Uri imageUri) {
                //发送媒体
                Log.d("inf", "******************************** " + imageUri);
            }
        });
//        slyceMessagingFragment.setLoadMoreMessagesListener(new LoadMoreMessagesListener() {
//            @Override
//            public List<Message> loadMoreMessages() {
//               List<Message> list  =new ArrayList<Message>();
//                list.add(new TextMessage());
//                return list;
//            }
//        });
        slyceMessagingFragment.setMoreMessagesExist(false);

        //lodeNewInfo
        scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
        httpThreadString=new HttpThreadString(getMessHandler,this, getmap, null);
        scheduleTaskExecutor.scheduleAtFixedRate( httpThreadString, 3, 3, TimeUnit.SECONDS);
    }

    private void CreateMap() {
        getmap=new HashMap();
        getmap.put("method","getMessage.action");
        getmap.put("uid", "" + sendid);
        getmap.put("tid", "" + recid);
        sendmap = new HashMap();
        sendmap.put("method","sendMessage.action");
        sendmap.put("uid", "" + sendid);
        sendmap.put("tid", "" + recid);
        sendmap.put("type", "TEXT");
        sendmap.put("content", "");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        httpThreadString.interrupt();
        scheduleTaskExecutor.shutdown();
    }
    private void CreateHandler() {
        getMessHandler=new android.os.Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                Bundle b=msg.getData();
                String uri=b.getString("state");
                Log.i("getmess",""+uri);
                if(uri.contains("uid"))
                {
                    Type type = new TypeToken<ArrayList<MyMessage>>() {}.getType();
                    ArrayList<MyMessage> jsonObjects = new Gson().fromJson(uri, type);

                    for (MyMessage infoitem : jsonObjects)
                    {
                        Log.i("Message", "" + infoitem);
                        TextMessage textMessage = new TextMessage();
                        textMessage.setText(infoitem.getContent());
                        textMessage.setAvatarUrl(recicon);//头像
                        textMessage.setDisplayName(String.valueOf(sendid));
                        textMessage.setUserId("LP");
                        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date = null;
                        try {
                            date =sim.parse(infoitem.getSendtime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textMessage.setDate(date.getTime());
                        textMessage.setSource(MessageSource.EXTERNAL_USER);
                        slyceMessagingFragment.addNewMessage(textMessage);
                    }
                }
            }
        };
    }
}
