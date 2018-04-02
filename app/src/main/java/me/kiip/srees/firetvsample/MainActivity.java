package me.kiip.srees.firetvsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.kiip.sdk.Poptart;
import me.kiip.sdk.Kiip;
import me.kiip.sdk.KiipNativeRewardView;

/**
 * Created by srees on 3/21/2018.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private KiipNativeRewardView mRewardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRewardView = (KiipNativeRewardView) findViewById(R.id.kiip_native_reward_view);

        Button defaultButton = (Button) findViewById(R.id.defaultnotification);
        Button nativeButton = (Button) findViewById(R.id.nativeFixed);
        Button recyclerButton = (Button) findViewById(R.id.recyclerView);

        defaultButton.setOnClickListener(this);
        nativeButton.setOnClickListener(this);
        recyclerButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.defaultnotification:
                defaultNotif();
                break;
            case R.id.nativeFixed:
                nativeNotif();;
                break;
            case R.id.recyclerView:
                recyclerNotif(view);
                break;
            default:
                break;
        }
    }
    public void defaultNotif(){
        Kiip.getInstance().saveMoment("Default Notification", new Kiip.Callback() {

            @Override
            public void onFinished(Kiip kiip, Poptart poptart) {
                if (poptart == null) {

                } else {
                    onPoptart(poptart);
                }
            }

            @Override
            public void onFailed(Kiip kiip, Exception exception) {
                // handle failure
            }
        });
    }

    public void nativeNotif(){

        Kiip.getInstance().saveMoment("Native Notification", new Kiip.Callback() {
            @Override
            public void onFailed(Kiip kiip, Exception exception) {
                //Log.e(TAG, exception.toString());
            }

            @Override
            public void onFinished( Kiip kiip, Poptart poptart) {
                if (poptart != null) {
                    //Pass the KiipNativeRewardView instance to render the native reward in the view component
                    //defined in layout.xml (me.kiip.sdk.KiipNativeRewardView)
                    poptart.showNativeReward(mRewardView);
                }
            }
        });
    }

    public void recyclerNotif(View v){
        Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
        startActivity(intent);
    }
}
