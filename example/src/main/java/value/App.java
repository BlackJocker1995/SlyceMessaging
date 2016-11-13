package value;

import android.app.Application;
import android.content.Context;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {

    public static Context mContext;
    public static String url="http://139.162.30.26:8080";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
