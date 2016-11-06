package ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import adapter.FriendListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;
import netwrok.HttpThreadString;
import widget.SimpleDividerItemDecoration;

/**
 * Created by rain on 2016/9/30.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView.LayoutManager mLayoutManager;
    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    FriendListAdapter friendListAdapter;

    private SharedPreferences sp;
    private  Intent intent;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundler=msg.getData();
            String url=bundler.getString("state");
            Log.i("friendsList", "" + url);
            if(url.contains("friendid")) {
                friendListAdapter.network(url);
                friendListAdapter.notifyDataSetChanged();
            }else{
                Snackbar.make(getWindow().getDecorView(),"错误",Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        friendListAdapter=new FriendListAdapter(this);
        mRecyclerView.setAdapter(friendListAdapter);//设置Adapter
        onRefresh();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onRefresh() {
        int stid = sp.getInt("user_id",0);
        Map map=new HashMap();
        map.put("method", "friendList.action");
        map.put("id",String.valueOf(stid));
        new HttpThreadString(handler,MainActivity.this,map, null).start();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //主菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.tongzhi:
                intent = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_zl:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_about:
                 intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 实现再按一次退出提醒
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Snackbar.make(drawer_layout, "再按一次推出", Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                sp.edit().putBoolean("CONNECT",false).commit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean prepareIntent(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
        return true;
    }


}

