package com.miguelcatalan.materialsearchview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button defaultButton;
    private Button themedButton;
    private Button voiceButton;
    private Button stickyButton;
    private Button tabButton;
    private Button inputTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultButton = (Button) findViewById(R.id.button_default);
        defaultButton.setOnClickListener(this);
        themedButton = (Button) findViewById(R.id.button_themed);
        themedButton.setOnClickListener(this);
        voiceButton = (Button) findViewById(R.id.button_voice);
        voiceButton.setOnClickListener(this);
        stickyButton = (Button) findViewById(R.id.button_sticky);
        stickyButton.setOnClickListener(this);
        tabButton = (Button) findViewById(R.id.button_tab);
        tabButton.setOnClickListener(this);
        inputTypeButton = (Button) findViewById(R.id.button_input_type);
        inputTypeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button_default:
                intent = new Intent(this, DefaultActivity.class);
                break;
            case R.id.button_themed:
                intent = new Intent(this, ColoredActivity.class);
                break;
            case R.id.button_voice:
                intent = new Intent(this, VoiceActivity.class);
                break;
            case R.id.button_sticky:
                intent = new Intent(this, StickyActivity.class);
                break;
            case R.id.button_tab:
                intent = new Intent(this, TabActivity.class);
                break;
            case R.id.button_input_type:
                intent = new Intent(this, InputTypeActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
