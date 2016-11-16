package ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;


public class ZiliaoActivity extends AppCompatActivity {

    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.toolbar)
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("资料");

        tvEmail.requestFocus();
        tvEmail.setText(sp.getString("user_name",""));
        tvUser.setText(sp.getString("name",""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
