package com.stellaquila.chatlayout.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stellaquila.ChatLayout;

public class ChatLayoutExample extends AppCompatActivity {

    ChatLayout chatLayout;
    Button toggle;
    boolean click = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout_example);
        chatLayout = findViewById(R.id.chat_layout);
        toggle = findViewById(R.id.togglebutton);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                if(click)
                    params.gravity = Gravity.END;
                else
                    params.gravity = Gravity.START;

                chatLayout.setLayoutParams(params);
                chatLayout.setSender(click);
                click=!click;
            }
        });
    }
}
