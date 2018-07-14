package com.example.lenovo.client.ContentWindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.client.ContentWindow.ContentActivity;
import com.example.lenovo.client.ContentWindow.DocumentAdapter;
import com.example.lenovo.client.Database.DocumentBean;
import com.example.lenovo.client.R;

import java.util.LinkedList;

public class MyFragment extends Fragment {
    private String name;
    private Context mContext;
    private DocumentAdapter adapterDownload;
    private ListView listView;
    private LinkedList<DocumentBean> doucumentList;
    private String query;
    public  MyFragment()
    {

    }
    @SuppressLint("ValidFragment")
    public  MyFragment(String name, Context mContext , LinkedList<DocumentBean> doucumentList,String query)
    {
        this.name=name;
        this.mContext=mContext;
        this.doucumentList=doucumentList;
        this.query=query;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout,container,false);
        adapterDownload = new DocumentAdapter(mContext,R.layout.item_layout,doucumentList,name);
        listView= view.findViewById(R.id.document_content);
        listView.setAdapter(adapterDownload);
        //设置listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DocumentBean document = doucumentList.get(i);
                if(name.equals("查询"))
                {
                    Intent intent= new  Intent(mContext,ContentActivity.class);
                    intent.putExtra("title",document.getTitle());
                    intent.putExtra("content",document.getAllContent());
                    startActivity(intent);
                    //Toast.makeText(mContext,"打开内容",Toast.LENGTH_SHORT);
                }
                else if(name.equals("历史"))
                {
                    Uri uri = Uri.parse(document.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                  //  Toast.makeText(mContext,"打开网页",Toast.LENGTH_SHORT);
                }


            }
        });
        return view;
    }
}
