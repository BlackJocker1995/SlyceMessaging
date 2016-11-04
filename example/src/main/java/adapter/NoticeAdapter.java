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
import value.Mess;

/**
 * Created by rain on 2016/10/26.
 */
public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<Mess> messArrayList;
    public MaterialDialog materialDialog;
    public NoticeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        materialDialog = new MaterialDialog(context);
        messArrayList = new ArrayList<Mess>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessViewHolder(mLayoutInflater.inflate(R.layout.mess_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessViewHolder)holder).request.setText(messArrayList.get(position).getName()+"  "+"请求加你为好友");
        ((MessViewHolder)holder).id = messArrayList.get(position).getSend_id();
        ((MessViewHolder)holder).text = messArrayList.get(position).getName();
        ((MessViewHolder)holder).position = position;
    }

    @Override
    public int getItemCount() {
        return messArrayList.size();
    }

   public void netWork(ArrayList<Mess> list){
        for(Mess it : list){
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
        materialDialog.setTitle("好友请求");
        materialDialog.setCanceledOnTouchOutside(true);
        StringBuffer sb = new StringBuffer();
        sb.append("ID: "+String.valueOf(id)+"\n");
        sb.append("Name: "+ text +"\n");
        sb.append("请求添加你为好友");
        materialDialog.setMessage(sb.toString());

        materialDialog.setPositiveButton("同意", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messArrayList.remove(position);
                accept();
                materialDialog.dismiss();
            }
        });

        materialDialog.setNegativeButton("拒绝", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messArrayList.remove(position);
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
