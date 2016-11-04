package ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adapter.SearchListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import it.snipsnap.slyce_messaging_example.R;

/**
 * Created by rain on 2016/11/3.
 */
public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.search_input)
    EditText mEditText;
    @Bind(R.id.search_button)
    Button mButton;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.cardList)
    RecyclerView cardList;

    String search_id;
    SearchListAdapter searchListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("查找好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardList.setLayoutManager(new LinearLayoutManager(this));
        searchListAdapter= new SearchListAdapter(this);
        cardList.setAdapter(searchListAdapter);
        cardList.setHasFixedSize(true);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_id=mEditText.getText().toString();
                AddFriend(search_id);
            }
        });
    }

    private void AddFriend(String search_id) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
