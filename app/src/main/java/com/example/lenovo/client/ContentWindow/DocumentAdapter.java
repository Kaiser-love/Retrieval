package com.example.lenovo.client.ContentWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.client.Database.DocumentBean;
import com.example.lenovo.client.R;

import java.util.LinkedList;

public class DocumentAdapter extends ArrayAdapter<DocumentBean> {
    private LinkedList<DocumentBean> mData;
    private int resourceId;
    private Context mContext;
    private String frament_name;
    public DocumentAdapter(Context context, int resource, LinkedList<DocumentBean> objects,String name) {
        super(context, resource, objects);
        mContext=context;
        resourceId=resource;
        mData=objects;
        frament_name=name;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DocumentBean document = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)view.findViewById(R.id.item_title);
            viewHolder.content = (TextView)view.findViewById(R.id.item_content);
            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); //重新获取viewHolder
        }
        viewHolder.title.setText("查询 ："+document.getQuery());
        if(frament_name.equals("查询")) {
            String str = document.getContnetAboutquery();
            if (str != null)
                viewHolder.content.setText("论文内容：\r\n"+document.getContnetAboutquery());
            else
                viewHolder.content.setText("论文内容：\r\n"+document.getAllContent());
        }
        else if(frament_name.equals("历史")) {
            viewHolder.content.setText("论文题目："+document.getTitle()+"\r\n\r\n"+"查询结果："+document.getUrl()+"\r\n"+"查询时间："+document.getDate());
        }
        return view;
    }
    class ViewHolder{
        TextView title;
        TextView content;
    }
}
