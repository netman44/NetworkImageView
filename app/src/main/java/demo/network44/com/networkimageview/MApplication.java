package demo.network44.com.networkimageview;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-15 16:42
 * @version: 9.1.0
 */
public class MApplication extends Application{
    private static final String TAG = "MApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
