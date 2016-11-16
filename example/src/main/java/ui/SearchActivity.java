package ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;
import netwrok.HttpThreadString;
import value.AddInfo;

/**
 * Created by rain on 2016/11/3.
 */
public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_input)
    EditText mEditText;
    @BindView(R.id.search_button)
    Button mButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    String search_id;
    private SharedPreferences sp;
    private Handler addFriendHandler;
    ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("查找好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CreateHandler();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_id=mEditText.getText().toString();
                mEditText.clearFocus();
                mButton.setFocusable(true);
                AddFriend(search_id);
            }
        });
    }

    private void AddFriend(String search_id) {
        Map map = new HashMap();
        map.put("method", "friendRequest.action");
        map.put("email", search_id);
        map.put("uid", "" + sp.getInt("user_id", -1));
        new HttpThreadString(addFriendHandler,this,map,proDialog).start();
        createProgressBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void CreateHandler() {
        addFriendHandler=new android.os.Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                Bundle b=msg.getData();
                String uri=b.getString("state");
                Log.i("state", "" + uri);
                Gson gson = new Gson();
                AddInfo addInfo = gson.fromJson(uri, AddInfo.class);
                String str= addInfo.getState();
                if(str.equals("1000"))
                {
                    proDialog.dismiss();
                    Toast.makeText(SearchActivity.this,"已发送请求",Toast.LENGTH_SHORT).show();
                }else if(str.equals("1001")){
                    proDialog.dismiss();
                    Toast.makeText(SearchActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void createProgressBar() {
        proDialog = android.app.ProgressDialog.show(SearchActivity.this, "请等待", "请求发送中！");
        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000);
                } catch (InterruptedException e)
                {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
                proDialog.dismiss();//不写会程序会卡死。
            }
        };
        thread.start();
    }
}
