package kmitl.esl.ultimate.wifirobot;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
//    Button connBtn = (Button) findViewById(R.id.connBtn);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_main_screen);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Button upBtn = (Button)findViewById(R.id.upBtn);
        Button downBtn = (Button)findViewById(R.id.downBtn);
        Button leftBtn = (Button)findViewById(R.id.leftBtn);
        Button rightBtn = (Button)findViewById(R.id.rightBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpConnection().execute("http://128.199.158.8:3000/connect/up");
            }
        });
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpConnection().execute("http://128.199.158.8:3000/connect/down");
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpConnection().execute("http://128.199.158.8:3000/connect/left");
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpConnection().execute("http://128.199.158.8:3000/connect/right");
            }
        });

    }

    protected class httpConnection extends AsyncTask<String, Integer, String>{
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
