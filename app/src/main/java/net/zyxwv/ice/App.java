package net.zyxwv.ice;

/**
 * Created by clyde on 12/30/2017.
 */
import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
