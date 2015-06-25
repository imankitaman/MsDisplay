package task.ankit.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> result, time;
    Context context;

    private static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, ArrayList<String> msgs, ArrayList<String> time) {
        // TODO Auto-generated constructor stub
        result = msgs;
        this.time = time;

        context = mainActivity;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        TextView tvmsg, tvtime, tvhead;
        ImageView img;
    }

    Bitmap loader;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.customlist, null);
        holder.tvmsg = (TextView) rowView.findViewById(R.id.tvmsg);
        holder.tvtime = (TextView) rowView.findViewById(R.id.tvtime);
        holder.img = (ImageView) rowView.findViewById(R.id.imgid);

        holder.tvmsg.setText(result.get(position));
        holder.tvtime.setText(time.get(position));
        // Set image Source to img.setResource from db !!!!!!!!!!!!

        getimage(holder.img);

        String path = Environment.getExternalStorageDirectory() + "/TempImages/profile.png";
        File imgFile = new File(path);
        if (imgFile.exists()) {
            loader = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }

        return rowView;
    }

    void getimage(ImageView img) {

        loader = BitmapFactory.decodeResource(context.getResources(), R.drawable.img);
        // Image url
        String image_url = "https://slack-files.com/files-tmb/T043116HE-F060B6S94-e165eab1af/ic_launcher_360.png";

        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(context.getApplicationContext());

        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(image_url, loader, img);

    }

}
