package task.ankit.demo;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    ListView lv;
    EditText etmsg;
    Button btsend;
    private DBHelper mydb;
    ArrayList<String> array_msgs;
    ArrayList<String> array_time;
    Calendar c;
    ImageView imageView;
    Bitmap imgbm = null;
    String src = "https://slack-files.com/files-tmb/T043116HE-F060B6S94-e165eab1af/ic_launcher_360.png";
    CustomAdapter adapter = null;
    DownloadAsyncTask dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

       // calling android.support.v7.widget.Toolbar in ActionBar
        setSupportActionBar((Toolbar) findViewById(R.id.actionbar));

        btsend = (Button) findViewById(R.id.btsend);
        etmsg = (EditText) findViewById(R.id.etmsg);
        lv = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.image);

     //Onsend button press
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etmsg.getText().toString().length() != 0) {
                    String am_pm = " ";

                    c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);

                    if (c.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "AM";
                    else if (c.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "PM";
                    
                    update(hour, minute, am_pm);
                    int date = c.get(Calendar.DATE);
                    int seconds = c.get(Calendar.SECOND);

                 //Storing time in StringBuilder
                    StringBuilder sb = new StringBuilder();
                    sb.append(date);
                    sb.append("@");
                    sb.append(hour);
                    sb.append(":");
                    sb.append(minute);
                    sb.append(" ");
                    sb.append(am_pm);
                    sb.append("@");
                    sb.append(seconds);

                    mydb.insertMessage(etmsg.getText().toString(), sb);

                    etmsg.setText("");
                }
            }
        });
        populate();
        dt = new DownloadAsyncTask();
        dt.execute(src);
    }

  //to update the time in list view
    void update(int hour, int minute, String am_pm) {
        array_msgs.add(etmsg.getText().toString());

        String str = "" + hour + ":" + minute + " " + am_pm;
        array_time.add(str);

        adapter = new CustomAdapter(this, array_msgs, array_time, imgbm);

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //to Update Image in list view
    void updateimg() {

        adapter = new CustomAdapter(this, array_msgs, array_time, imgbm);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // to update the message and time from db
    void populate() {

        mydb = new DBHelper(this);

    //Get msgs and time from Sqlite db

        array_msgs = mydb.getAllMessages();
        array_time = mydb.getAllTime();

        adapter = new CustomAdapter(this, array_msgs, array_time, imgbm);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Loading image using AsyncTask
    public class DownloadAsyncTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... arg0) {
            Bitmap b = null;
            try {
                b = BitmapFactory.decodeStream((InputStream) new URL(arg0[0]).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imgbm=result;
                updateimg();
            }
        }

    }
}

