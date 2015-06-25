package task.ankit.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "msg_db.db";
    public static final String MSG_TABLE_NAME = "messages";
    public static final String MSG_COLUMN_MSG = "msg";
    public static final String MSG_COLUMN_TIME = "time";


    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table messages " +
                        "(time text primary key, msg text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS messages");
        onCreate(db);
    }

    public boolean insertMessage(String msg, StringBuilder time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String tm = time.toString();
        contentValues.put("time", tm);
        contentValues.put("msg", msg);
        db.insert("messages", null, contentValues);
        return true;
    }

    public ArrayList<String> getAllMessages() {
        ArrayList<String> array_msg = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from messages", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_msg.add(res.getString(res.getColumnIndex(MSG_COLUMN_MSG)));
            res.moveToNext();
        }
        return array_msg;
    }

    public ArrayList<String> getAllTime() {
        ArrayList<String> array_time = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from messages", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String tm = res.getString(res.getColumnIndex(MSG_COLUMN_TIME));
            String[] time = tm.split("@");
            array_time.add(time[1]);
            res.moveToNext();
        }
        return array_time;
    }

}