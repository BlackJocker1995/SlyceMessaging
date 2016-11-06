package ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import adapter.NoticeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;
import netwrok.HttpThreadString;

/**
 * Created by rain on 2016/10/26.
 * rain
 */
public class NoticeActivity extends AppCompatActivity {

    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    public NoticeAdapter noticeAdapter;
    private HttpThreadString httpThreadString;
    private SharedPreferences sp;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        noticeAdapter=new NoticeAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(noticeAdapter);
        toolbar.setTitle("请求消息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CreateHanlder();
        onRefresh();

    }
    public void onRefresh() {
        int stid = sp.getInt("user_id",0);
        Map map=new HashMap();
        map.put("method", "getFriendRequest.action");
        map.put("uid", String.valueOf(stid));
        new HttpThreadString(handler,NoticeActivity.this,map, null).start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId()== R.id.home){
                finish();//close
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void CreateHanlder(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundler=msg.getData();
                String url=bundler.getString("state");
                Log.i("friendsRequest", "" + url);
                if(url.contains("type")) {
                    noticeAdapter.network(url);
                }else{
                    Snackbar.make(getWindow().getDecorView(), "没有请求", Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }
}
