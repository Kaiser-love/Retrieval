package com.example.lenovo.client.LoginWindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.client.MainWindow.MainActivity;
import com.example.lenovo.client.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_account;
    private EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init()
    {
        findViewById(R.id.btn_login).setOnClickListener(this);
        et_account=findViewById(R.id.et_account);
        et_password=findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                String account=et_account.getText().toString();
                String password=et_password.getText().toString();
                if(account.equals("user")  && password.equals("123456")) {
                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();
                }
                else
                    Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
