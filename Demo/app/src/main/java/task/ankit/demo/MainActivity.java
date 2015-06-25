package task.ankit.demo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    ListView lv;
    Context context;
    EditText etmsg;
    Button btsend;
    private DBHelper mydb;
    ArrayList<String> array_msgs;
    ArrayList<String> array_time;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.winners);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btsend = (Button) findViewById(R.id.btsend);
        etmsg = (EditText) findViewById(R.id.etmsg);
        lv = (ListView) findViewById(R.id.listView);


        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmsg.getText().toString().length() != 0) {
                    String am_pm=" ";

                    c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);

                    if (c.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "AM";
                    else if (c.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "PM";

                    update(hour, minute,am_pm);

                    int date = c.get(Calendar.DATE);
                    int seconds = c.get(Calendar.SECOND);

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
    }

    CustomAdapter adapter = null;

    void update(int hour, int minute, String am_pm) {
        array_msgs.add(etmsg.getText().toString());

        String str = "" + hour + ":" + minute +" "+am_pm;
        array_time.add(str);

        adapter = new CustomAdapter(this, array_msgs, array_time);
        lv.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

        lv.setSelection(lv.getAdapter().getCount() - 1);
    }

    void winnerslist(ArrayList<String> msgs, ArrayList<String> time) {

        context = this;
        lv.setAdapter(new CustomAdapter(this, msgs, time));
    }

    void populate() {

        mydb = new DBHelper(this);
        //Get msgs and time from Sqlite db

        array_msgs = mydb.getAllMessages();
        array_time = mydb.getAllTime();

        winnerslist(array_msgs, array_time);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
