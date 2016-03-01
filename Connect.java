package kmitl.esl.ultimate.wifirobot;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by ulTimate on 1/6/2016.
 */

public class Connect extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String USER_AGENT = "Mozilla/5.0";
    String address = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            address = extras.getString("inpAddress");
        }
        Button upBtn = (Button)findViewById(R.id.upBtn);
        Button downBtn = (Button)findViewById(R.id.downBtn);
        Button leftBtn = (Button)findViewById(R.id.leftBtn);
        Button rightBtn = (Button)findViewById(R.id.rightBtn);
        final String finalAddress = address;
        upBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":3000/connect/up/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":3000/connect/up/0"); break;
                }
                return true;
            }
        });
        downBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":3000/connect/down/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":3000/connect/down/0"); break;
                }
                return true;
            }
        });
        leftBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":3000/connect/left/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":3000/connect/left/0"); break;
                }
                return true;
            }
        });
        rightBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":3000/connect/right/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":3000/connect/right/0"); break;
                }
                return true;
            }
        });
    }

    protected class httpConnection extends AsyncTask<String, Integer, String>{
        ProgressDialog dialog;
        @Override
        protected String doInBackground(String... urls) {
            String parsedString="";
            try {

                URL url = new URL(urls[0]);
                URLConnection conn = url.openConnection();

                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                InputStream is = httpConn.getInputStream();
                parsedString = convertinputStreamToString(is);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return parsedString;
        }
        public String convertinputStreamToString(InputStream ists)
                throws IOException {
            if (ists != null) {
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                    BufferedReader r1 = new BufferedReader(new InputStreamReader(
                            ists, "UTF-8"));
                    while ((line = r1.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } finally {
                    ists.close();
                }
                return sb.toString();
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String parsedString) {
            TextView opt = (TextView)findViewById(R.id.responseTest);
            opt.setText(parsedString);
            dialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog = ProgressDialog.show(Connect.this, "",
                    "Please wait...", true);

        }
    }

    @Override
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_reconnect) {
            new httpConnection().execute("http://"+ address +":3000/test");
            return true;
        }
        else if (id == R.id.menu_shutdown){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Turning off robot")
                    .setMessage("Are you sure you want to turn off robot?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new httpConnection().execute("http://" + address + ":3000/shutdown");
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        else if(id == R.id.menu_viewimages){
            Intent viewImg = new Intent(getApplicationContext(), ViewImages.class);
            startActivity(viewImg);
        }

        return super.onOptionsItemSelected(item);
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
