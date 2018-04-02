package me.kiip.srees.firetvsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import me.kiip.sdk.Kiip;
import me.kiip.sdk.KiipNativeRewardViewHolder;
import me.kiip.sdk.Poptart;

/**
 * Created by srees on 3/23/2018.
 */

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> dataList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        Locale[] locales = Locale.getAvailableLocales();
        dataList = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !dataList.contains(country)) {
                dataList.add(country);
            }
        }
        Collections.sort(dataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new DataAdapter(dataList));
    }

    private class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> dataList;
        private static final int TYPE_TEXT_VIEW = 0;

        //1. Define ItemViewType as TYPE_REWARD_VIEW to identify the reward row type
        private static final int TYPE_REWARD_VIEW = 1;

        public DataAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //2. Inflate Reward Row
            View rewardRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_rewardview_row, parent, false);

            View listRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_recyclerview_listview,parent,false);

            switch (viewType) {
                case TYPE_TEXT_VIEW:
                    return new SimpleTextViewHolder(listRowView);
                case TYPE_REWARD_VIEW:
                    //3.When ItemViewType is TYPE_REWARD_VIEW provide instanceof KiipNativeRewardViewHolder
                    return new KiipNativeRewardViewHolder(rewardRowView, R.id.kiip_native_reward_view);
            }

            return null;
        }

        @Override
        public int getItemViewType(int position) {
            //4. Define in which row position we want to show the reward.
            if (position == 0 || position == 5)
                return TYPE_REWARD_VIEW;
            return TYPE_TEXT_VIEW;
        }

        @Override
        public int getItemCount() {
            //5. Handle the ItemCount when rewardview added.
            return (null != dataList ? dataList.size() + 1 : 0);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case TYPE_TEXT_VIEW:
                    String data = dataList.get(position - 1);
                    SimpleTextViewHolder textHolder = (SimpleTextViewHolder)holder;
                    textHolder.tv.setText(data);
                    break;

                //6. Handle the RewardView type and call the saveMoment to fetch the reward.
                case TYPE_REWARD_VIEW:
                    final KiipNativeRewardViewHolder rewardHolder = (KiipNativeRewardViewHolder)holder;
                    if (!rewardHolder.isRewardLoaded()) {
                        Kiip.getInstance().saveMoment("Native Notification", new Kiip.Callback() {
                            @Override
                            public void onFailed(Kiip kiip, Exception exception) {
                                //Handle exception
                            }
                            @Override
                            public void onFinished(Kiip kiip, Poptart poptart) {
                                if (poptart != null) {
                                    //Pass the KiipNativeRewardView instance of KiipNativeRewardViewHolder (rewardHolder)
                                    poptart.showNativeReward(rewardHolder.nativeRewardView);
                                }
                            }
                        });
                    }

                    break;
            }
        }

        class SimpleTextViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public SimpleTextViewHolder(View view){
                super(view);
                tv = (TextView) view.findViewById(R.id.title);
            }
        }
    }
}

