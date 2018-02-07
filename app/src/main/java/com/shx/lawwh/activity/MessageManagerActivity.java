package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;

/**
 * Created by zhou on 2018/2/7.
 */

public class MessageManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_manager);
        getTopbar().setTitle("通知管理");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.sb_message);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else{

                }
            }
        });
    }
}
