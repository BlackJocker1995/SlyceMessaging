package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import it.snipsnap.slyce_messaging_example.R;
import it.snipsnap.slyce_messaging_example.SendActivity;
import netwrok.FriendNet;
import value.Friends;

/**
 * Created by rain on 2016/4/13.
 */
public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public ArrayList<Friends> friends;
    private SharedPreferences sp;

    public FriendListAdapter(Context context) {
        SetFriend();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        Collections.sort(this.friends);
    }

    private void SetFriend()
    {
        friends=new ArrayList<Friends>();
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,1));
        friends.add(new Friends("张三",2,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",false,2));
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,0));
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,0));
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,1));
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,2));
        friends.add(new Friends("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true,0));

    }

    public void network(String url) {
        Type type = new TypeToken<ArrayList<Friends>>() {}.getType();
        ArrayList<Friends> jsonObjects = new Gson().fromJson(url, type);

        for (Friends infoitem : jsonObjects)
        {
            friends.add(0,infoitem);
        }
    }
    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
            return 0;
    }

    //adapter内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView person;
        private TextView messageNum;
        private LinearLayout background;
        public ContentViewHolder(final View itemView) {
            super(itemView);
            person  = (TextView) itemView.findViewById(R.id.friendText);
            icon = (ImageView) itemView.findViewById(R.id.image_icon);
            messageNum = (TextView) itemView.findViewById(R.id.mess_number);
            background = (LinearLayout) itemView.findViewById(R.id.background);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent userActivity = new Intent(itemView.getContext(), SendActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    userActivity.putExtra("ACCEPT", person.getText().toString());
                    itemView.getContext().startActivity(userActivity);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.friends_item,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ContentViewHolder)holder).icon.setImageURI(Uri.parse(friends.get(position).getIcon()));
        ((ContentViewHolder) holder).person.setText(friends.get(position).getName());
        if(friends.get(position).getMessageNumber()!= 0)((ContentViewHolder) holder).messageNum.setText(String.valueOf(friends.get(position).getMessageNumber()));
        //not online
        if (friends.get(position).getOnline()==false) {
            ((ContentViewHolder) holder).background.setBackgroundColor(Color.GRAY);
        }
    }
    @Override
    public int getItemCount() {
        return friends.size();
    }


}

