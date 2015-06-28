package task.ankit.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> result, time;
    Context context;
    Bitmap imgbm = null;
    String src = "https://slack-files.com/files-tmb/T043116HE-F060B6S94-e165eab1af/ic_launcher_360.png";

    private static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, ArrayList<String> msgs, ArrayList<String> time, Bitmap imgbm) {
        // TODO Auto-generated constructor stub
        result = msgs;
        this.time = time;
        this.imgbm = imgbm;

        context = mainActivity;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CustomAdapter(Bitmap imgbm) {
        this.imgbm = imgbm;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tvmsg, tvtime;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView = convertView;
        Holder vholder;

            // check condition if row equal to null then execute

        if (rowView == null) {

            vholder = new Holder();
            rowView = inflater.inflate(R.layout.customlist, null);
            rowView.setTag(vholder);
        } else {

            vholder = (Holder) rowView.getTag();
        }

        vholder.tvmsg = (TextView) rowView.findViewById(R.id.tvmsg);
        vholder.tvtime = (TextView) rowView.findViewById(R.id.tvtime);
        vholder.img = (ImageView) rowView.findViewById(R.id.imgid);

        vholder.tvmsg.setText(result.get(position));
        vholder.tvtime.setText(time.get(position));

      //check condition if image is equal to null then change
        if (imgbm != null) {
            vholder.img.setImageBitmap(imgbm);
        }

        return rowView;
    }


}





