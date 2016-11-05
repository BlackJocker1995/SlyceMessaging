package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.snipsnap.slyce_messaging_example.R;
import me.drakeet.materialdialog.MaterialDialog;
import value.User;

/**
 * Created by rain on 2016/11/5.
 */
public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<User> messArrayList;
    public MaterialDialog materialDialog;
    public SearchListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        materialDialog = new MaterialDialog(context);
        messArrayList = new ArrayList<User>();
    }

    private void SetFriend()
    {

        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));
        messArrayList.add(new User("张三",2,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",false));
        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));
        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));
        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));
        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));
        messArrayList.add(new User("张三",1,"https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5",true));

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessViewHolder(mLayoutInflater.inflate(R.layout.mess_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessViewHolder)holder).request.setText(messArrayList.get(position).getName()+"#"+messArrayList.get(position).getPeopleID());
        ((MessViewHolder)holder).id = messArrayList.get(position).getPeopleID();
        ((MessViewHolder)holder).text = messArrayList.get(position).getName();
        ((MessViewHolder)holder).position = position;
    }

    @Override
    public int getItemCount() {
        return messArrayList.size();
    }

    public void netWork(ArrayList<User> list){
        for(User it : list){
            messArrayList.add(it);
        }
        notifyDataSetChanged();
    }

    public  class MessViewHolder extends RecyclerView.ViewHolder{
        private TextView request;
        private String text;
        private int id;
        private int  position;
        public MessViewHolder(View itemView) {
            super(itemView);
            request = (TextView) itemView.findViewById(R.id.request_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insure(id,text,position);
                }
            });
        }
    }

    public void insure(int id, String text, final int position) {
        //还有请求确认界面
        materialDialog.setTitle("添加好友");
        materialDialog.setCanceledOnTouchOutside(true);
        StringBuffer sb = new StringBuffer();
        sb.append("ID: "+String.valueOf(id)+"\n");
        sb.append("Name: "+ text +"\n");
        sb.append("是否添加为好友？");
        materialDialog.setMessage(sb.toString());

        materialDialog.setPositiveButton("同意", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept();
                materialDialog.dismiss();
            }
        });

        materialDialog.setNegativeButton("拒绝", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    private void cancel() {
        notifyDataSetChanged();
    }

    private void accept() {
        notifyDataSetChanged();
    }

}
