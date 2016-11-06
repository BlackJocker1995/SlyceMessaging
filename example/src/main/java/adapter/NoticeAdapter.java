package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.snipsnap.slyce_messaging_example.R;
import me.drakeet.materialdialog.MaterialDialog;
import netwrok.HttpThreadString;
import value.RequestInfo;

/**
 * Created by rain on 2016/10/26.
 */
public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<RequestInfo> messArrayList;
    public MaterialDialog materialDialog;
    private SharedPreferences sp;
    public NoticeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        materialDialog = new MaterialDialog(context);
        messArrayList = new ArrayList<RequestInfo>();
        sp = context.getSharedPreferences("userinfo", context.MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessViewHolder(mLayoutInflater.inflate(R.layout.mess_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessViewHolder)holder).request.setText(messArrayList.get(position).getUid()+"  "+"请求加你为好友");
        ((MessViewHolder)holder).tid=messArrayList.get(position).getUid();
        ((MessViewHolder)holder).position = position;
    }

    @Override
    public int getItemCount() {
        return messArrayList.size();
    }

    public void network(String url) {
        Type type = new TypeToken<ArrayList<RequestInfo>>() {}.getType();
        ArrayList<RequestInfo> jsonObjects = new Gson().fromJson(url, type);
        for (RequestInfo infoitem : jsonObjects)
        {
            if(!messArrayList.contains(infoitem)) {
                messArrayList.add(0, infoitem);
                Log.i("requestMess", "" + infoitem);
            }
        }
        notifyDataSetChanged();
    }

    public  class MessViewHolder extends RecyclerView.ViewHolder{
        private TextView request;
        private String tid;
        private int position;
        public MessViewHolder(View itemView) {
            super(itemView);
            request = (TextView) itemView.findViewById(R.id.request_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uid = String.valueOf(sp.getInt("user_id",-1));
                    insure(uid,tid,position);
                }
            });
        }
    }

    public void insure(final String local, final String net,final   int position) {
        //还有请求确认界面
        materialDialog.setTitle("好友请求");
        materialDialog.setCanceledOnTouchOutside(true);
        StringBuffer sb = new StringBuffer();
        sb.append("ID: "+String.valueOf(net)+"\n");
        sb.append("请求添加你为好友");
        materialDialog.setMessage(sb.toString());

        materialDialog.setPositiveButton("同意", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messArrayList.remove(position);
                result(local,net,1);
                materialDialog.dismiss();
            }
        });

        materialDialog.setNegativeButton("拒绝", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messArrayList.remove(position);
                result(local,net,0);
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }
    private void result(String local,String net,int approve){
        Map map = new HashMap();
        map.put("method","friendVerify.action");
        map.put("uid", local);
        map.put("tid", net);
        map.put("approve",String.valueOf(approve));
        new HttpThreadString(null,null,map, null).start();
    }

}
