package me.kiip.srees.firetvsample;

import android.app.Application;
import android.util.Log;

import me.kiip.sdk.Kiip;
/**
 * Created by srees on 3/21/2018.
 */

public class App extends Application implements Kiip.OnContentListener{
    public static final String TAG = "KIIP";
    private static final String KIIP_APP_KEY = "23cc1239ef19189b4ad53b9b2df2e1cf";
    private static final String KIIP_APP_SECRET = "e27bd22d4a85189e8f7a7569431396e8";

    @Override
    public void onCreate() {
        super.onCreate();
        Kiip.init(this, KIIP_APP_KEY, KIIP_APP_SECRET);
        Kiip.getInstance().setOnContentListener(this);
        Kiip.getInstance().setTestMode(true);
    }

    @Override
    public void onContent(Kiip kiip, String contentID, int quantity, String transactionID, String signature){
        Log.d(TAG,"onContent recieved= " + quantity);
    }
}
