package ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;


public class ZiliaoActivity extends AppCompatActivity {

    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ziliao);
        ButterKnife.bind(this);
        WindowManager wm = this.getWindowManager();
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("资料");

        tvEmail.requestFocus();
        tvEmail.setText(sp.getString("user_name",""));
        tvUser.setText(sp.getString("name",""));
    }
}
