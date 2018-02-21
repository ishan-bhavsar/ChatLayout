package com.stellaquila.chatlayout.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stellaquila.ChatLayout;

/**
 * Created by Ishan Bhavsar on 09-02-2018.
 */

public class ChatLayoutExample extends AppCompatActivity {

    ChatLayout chatLayout;
    Button toggle;
    Button style;
    int istyle = ChatLayout.STYLE_WHATSAPP;
    boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout_example);
        chatLayout = findViewById(R.id.chat_layout);
        toggle = findViewById(R.id.togglebutton);
        style = findViewById(R.id.stylebutton);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                if (click) {
                    toggle.setText("Toggle: Sender");
                    params.gravity = Gravity.END;
                } else {
                    toggle.setText("Toggle: Receiver");
                    params.gravity = Gravity.START;
                }
                chatLayout.setLayoutParams(params);
                chatLayout.setSender(click);
                click = !click;
            }
        });

        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (istyle == ChatLayout.STYLE_WHATSAPP) {
                    style.setText("Style: IPhone");
                    istyle = ChatLayout.STYLE_IPHONE;
                } else {
                    style.setText("Style: WhatsApp");
                    istyle = ChatLayout.STYLE_WHATSAPP;
                }
                chatLayout.setStyle(istyle);
            }
        });
    }
}
