package ui.loginAndRegister;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        //自动登陆
        if(sp.getBoolean("AUTO",false)){
            TestLogin(sp.getString("user_name", ""), sp.getString("password", ""));
            Intent i2 = new Intent(this, MainActivity.class);
            startActivity(i2);
            finish();
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
                }
            }
        });

    }

    private boolean TestLogin(String stUserName, String stPassWord) {
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
}
