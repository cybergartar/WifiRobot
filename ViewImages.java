package kmitl.esl.ultimate.wifirobot;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.FileHandler;


/**
 * Created by ulTimate on 1/6/2016.
 */

public class ViewImages extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;
    private static final String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_images);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }
    Integer[] imageIDs = {R.drawable.jpg, R.drawable.jpg, R.drawable.jpg, R.drawable.jpg, R.drawable.jpg, R.drawable.jpg, R.drawable.jpg};
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Connect Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://kmitl.esl.ultimate.wifirobot/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "WifiRobotImages");
        file.mkdirs();

        if(file.isDirectory()){
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];
            for(int i=0; i<listFile.length; i++){
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();
            }
        }
        GridView gridImage = (GridView)findViewById(R.id.gridImage);
        gridImage.setAdapter(new ImageAdapter(this, FilePathStrings));

        gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getApplicationContext(), SingleImage.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", FilePathStrings);
                // Pass click position
                i.putExtra("position", position);
                startActivity(i);
            }

        });
    }

    public class ImageAdapter extends BaseAdapter{
        private Context context;
        private String[] filePath;
        public ImageAdapter(Context c, String[] fpath){
            context = c;
            filePath = fpath;
        }


        @Override //return the number of images
        public int getCount() {
            return listFile.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView == null){
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(320, 320));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(2, 2, 2, 2);
            }
            else {
                imageView = (ImageView) convertView;
            }
            Bitmap bmp = BitmapFactory.decodeFile(filePath[position]);
            imageView.setImageBitmap(bmp);
            return imageView;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Connect Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://kmitl.esl.ultimate.wifirobot/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



}
