package com.example.lenovo.client.MainWindow;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.client.ContentWindow.MyFragment;
import com.example.lenovo.client.Database.DBManager;
import com.example.lenovo.client.Database.DocumentBean;
import com.example.lenovo.client.R;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler handler;
    public class UIHander extends Handler{
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    Toast.makeText(MainActivity.this,"成功连接服务器",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    initFragment1();
                    break;
            }
        }
    }
    private EditText search;
    private Socket socket;
    private PrintWriter pw;
    private MyFragment f1;
    private MyFragment f2;
    private LinkedList<DocumentBean> documentlist=new LinkedList<DocumentBean>();
    private LinkedList<DocumentBean> historylist=new LinkedList<DocumentBean>();
    private String query="";
    private DBManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindListener();
        Connect();
        manager=new DBManager(this);
        historylist=manager.query();
    }
    private void bindView() {
        handler=new UIHander();
        search = findViewById(R.id.search);
    }
    private void bindListener() {
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_find).setOnClickListener(this);
        findViewById(R.id.btn_history).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
    }
    private void Connect()
    {
        new Thread(new Runnable() {//读线程
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.187.1", 8000);
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    int num=0;
                    Message ms=new Message();
                    while (true) {
                        StringBuffer content = new StringBuffer();
                        StringBuffer allcontent = new StringBuffer();
                        DocumentBean bean= new DocumentBean();
                        String temp=null;
                        allcontent.append(br.readLine()+"\r\n" );
                        while (!(temp = br.readLine()).equals("end")) {
                            allcontent.append(temp+"\r\n");
                            content.append(temp);
                            if(isEven(content.toString()))
                            {
                                bean.update(content.toString(),query);
                                content=new StringBuffer();
                            }
                        }
                        bean.setContent(allcontent.toString());
                        bean.setQuery(query);
                        bean.setDate(new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date()).toString());
                        documentlist.add(bean);
                        bean.print();
                        if(++num==5)
                        {
                            ms=new Message();
                            ms.what=1;
                            handler.sendMessage(ms);
                            num=0;
                        }
                        if(num==1) {
                            manager.addDocument(bean);
                            historylist.add(bean);
                        }
                    }
                } catch (Exception e) {
                    Log.i("Thread", e.toString());
                }
            }
        }).start();
    }
    protected boolean isEven(String str)
    {
        int sum=0;
        for(int i=0;i<str.length();i++)
            if(str.charAt(i)=='{'   ||  str.charAt(i)=='}' )
                sum++;
        if(sum%2==0)
            return true;
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                final String message = search.getText().toString();
                query=message;
                documentlist.clear();
                System.out.println(message);
                new Thread(new Runnable() {//写线程
                    @Override
                    public void run() {
                        try {
                            pw.println(message);
                            pw.flush();
                            search.setText("");
                        } catch (Exception e) {
                            Log.i("Thread", e.toString());
                        }
                    }
                }).start();
                break;
            case R.id.btn_find:
                initFragment1();
                break;
            case R.id.btn_history:
                initFragment2();
                break;
            case R.id.btn_refresh:
                break;
            case R.id.btn_home:
                break;
        }
    }
    //显示第一个fragment
    private void initFragment1(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        f1 = new MyFragment("查询",this,documentlist,query);
        transaction.replace(R.id.main_frame_layout, f1);
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(f1);
        //提交事务
        transaction.commit();
    }
    //显示第二个fragment
    private void initFragment2(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        f2 = new MyFragment("历史",this,historylist,query);
        transaction.replace(R.id.main_frame_layout,f2);
        hideFragment(transaction);
        transaction.show(f2);
        transaction.commit();
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(f1 != null){
            transaction.hide(f1);
        }
        if(f2 != null){
            transaction.hide(f2);
        }
    }
}
