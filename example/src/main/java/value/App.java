package value;

import android.app.Application;
import android.content.Context;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {

    public static Context mContext;
    String url="";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
