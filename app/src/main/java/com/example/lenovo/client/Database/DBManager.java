package com.example.lenovo.client.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    public void addDocument(DocumentBean doc)
    {
        db.beginTransaction();  //开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", doc.getTitle());
            cv.put("url", doc.getUrl());
            cv.put("date",doc.getDate());
            cv.put("find",doc.getQuery());
            //插入ContentValues中的数据
                db.insert("history", null, cv);
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }
    public LinkedList<DocumentBean> query() {
        LinkedList<DocumentBean> docs = new LinkedList<DocumentBean>();
        Cursor c = db.rawQuery("SELECT * FROM history", null);
        while (c.moveToNext()) {
            DocumentBean doc = new DocumentBean();
            doc.setTitle(c.getString(c.getColumnIndex("name")));
            doc.setURL(c.getString(c.getColumnIndex("url")));
            doc.setDate(c.getString(c.getColumnIndex("date")));
            doc.setQuery(c.getString(c.getColumnIndex("find")));
            docs.add(doc);
        }
        c.close();
        return docs;
    }
}