package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import adapter.NoticeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;

import value.Mess;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        noticeAdapter=new NoticeAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(noticeAdapter);
        /*静态测试数据*/
        ArrayList<Mess> templist =  test();
        noticeAdapter.netWork(templist);
        /**/
        toolbar.setTitle("请求消息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public ArrayList<Mess> test(){
        ArrayList<Mess> list = new ArrayList<Mess>();
        list.add(new Mess(3,"黄光杰"));
        list.add(new Mess(3,"黄光杰"));
        return list;
    }

}
