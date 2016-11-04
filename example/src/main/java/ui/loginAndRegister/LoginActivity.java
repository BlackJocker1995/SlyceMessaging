package ui.loginAndRegister;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import netwrok.HttpThreadString;
import netwrok.Userinfo;
import ui.MainActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.snipsnap.slyce_messaging_example.R;


public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_username)
    EditText username;
    @Bind(R.id.et_password)
    EditText userpassword;
    @Bind(R.id.bt_go)
    Button btGo;
    @Bind(R.id.cv)
    CardView cv;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.remember_pass)
    CheckBox remember;
    @Bind(R.id.login_auto)
    CheckBox login_auto;

    private SharedPreferences sp;
    Handler handler;
    ProgressDialog proDialog;
    String stUserName;
    String stPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        CreateHandler();

        //自动登陆
        if(sp.getBoolean("AUTO",false)){
            TestLogin(sp.getString("user_name", ""), sp.getString("password", ""));
            return;
        }
        //是否记住密码
        if (sp.getBoolean("REM", false)) {
            username.setText(sp.getString("user_name", ""));
            userpassword.setText(sp.getString("password", ""));
            remember.setChecked(true);
        } else {
            username.setText(sp.getString("user_name", ""));
            userpassword.setText("");
            login_auto.setChecked(false);
            remember.setChecked(false);
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    sp.edit().putBoolean("REM", true).commit();
                } else {
                    sp.edit().putBoolean("REM", false).commit();
                    sp.edit().putBoolean("AUTO", false).commit();
                    login_auto.setChecked(false);
                }
            }
        });
        login_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember.isChecked()) {
                    sp.edit().putBoolean("AUTO", true).commit();
                } else {
                    sp.edit().putBoolean("AUTO", false).commit();
                    sp.edit().putBoolean("REM", false).commit();
                    remember.setChecked(false);
                }
            }
        });

    }

    private void CreateHandler() {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b=msg.getData();
                String test=b.getString("state");
                if(test.contains("user_id"))
                {
                    proDialog.dismiss();
                    Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i2);

                    Gson gson = new Gson();
                    Userinfo userinfo = gson.fromJson(test, Userinfo.class);

                    sp.edit().putString("user_password", stPassWord).commit();
                    sp.edit().putString("user_name", userinfo.getUser_name()).commit();
                    sp.edit().putInt("user_id", userinfo.getUser_id()).commit();
                    finish();
                }else{
                    Snackbar.make(getWindow().getDecorView(),"登陆失败",Snackbar.LENGTH_SHORT).show();
                    proDialog.dismiss();
                }
            }
        };
    }

    private boolean TestLogin(String stUserName, String stPassWord) {
        Map map=new HashMap();
        map.put("method","login.php");
        map.put("login_mail", "" + stUserName);
        map.put("login_passwd", "" + stPassWord);
        new HttpThreadString(handler,getApplicationContext(),map,proDialog).start();
        createProgressBar();
        return true;
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(null);
                }
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                break;
            case R.id.bt_go:
                String stUserName = username.getText().toString();
                String stPassWord = userpassword.getText().toString();
                if (TestLogin(stUserName, stPassWord)) {
                    Intent i2 = new Intent(this, MainActivity.class);
                    startActivity(i2);
                    if (sp.getBoolean("REM", false)) {
                        sp.edit().putString("user_name", stUserName).commit();
                        sp.edit().putString("password", stPassWord).commit();
                    }
                    finish();
                    break;
                }
        }
    }
    private void createProgressBar() {
        proDialog = android.app.ProgressDialog.show(LoginActivity.this, "请等待", "数据传送中！");
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
