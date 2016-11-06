package netwrok;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oreo on 2016/10/18.
 */
public class GetImage extends  Thread{
        private String picurl="";
        private Handler handler;
         public ProgressDialog proDialog;
    private Context context;
    private static final int ERROR = 3;
    private static final int EXCEPTION = 4;

    public GetImage(String url, Handler handler, ProgressDialog proDialog,Context context) {
        this.picurl = url;
        this.handler = handler;
        this.proDialog = proDialog;
        this.context=context;
    }

    @Override
    public void run() {
        File file = new File(context.getCacheDir(), Base64.encodeToString(
                picurl.getBytes(), Base64.DEFAULT));
            System.out.println(picurl);
            // 通过http请求把图片获取下来。
            try {
                // 1.声明访问的路径， url 网络资源 http ftp rtsp
                URL url = new URL(picurl);
                // 2.通过路径得到一个连接 http的连接
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                // 3.判断服务器给我们返回的状态信息。
                // 200 成功 302 从定向 404资源没找到 5xx 服务器内部错误
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream is = conn.getInputStream();// png的图片
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    is.close();
                    fos.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(file
                            .getAbsolutePath());
                    //更新ui ，不能写在子线程
                    Message msg = new Message();
                    msg.obj = bitmap;
                    msg.what = 2;
                    handler.sendMessage(msg);
                    //iv.setImageBitmap(bitmap);
                } else {
                    // 请求失败
                    //土司更新ui，不能写在子线程
                    //Toast.makeText(this, "请求失败", 0).show();
                    Message msg = new Message();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //土司不能写在子线程
                //Toast.makeText(this, "发生异常，请求失败", 0).show();
                Message msg = new Message();
                msg.what = EXCEPTION;
                handler.sendMessage(msg);
            }
        }
}




