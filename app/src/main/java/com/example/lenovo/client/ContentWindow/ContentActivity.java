package com.example.lenovo.client.ContentWindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.client.MainWindow.MainActivity;
import com.example.lenovo.client.R;

public class ContentActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_content;
    private String query;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent=getIntent();
        tv_title=(TextView) findViewById(R.id.title);
        tv_content=(TextView) findViewById(R.id.content);
        tv_title.setText(intent.getStringExtra("title"));
        tv_content.setText(intent.getStringExtra("content"));
        query=intent.getStringExtra("query");
        Toast.makeText(this,"正在打开论文",Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        Intent i=new Intent();
        i.setClass(ContentActivity.this,MainActivity.class);
        startActivity(i);
        ContentActivity.this.finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i=new Intent();
            i.setClass(ContentActivity.this,MainActivity.class);
            startActivity(i);
            ContentActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
