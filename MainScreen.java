package kmitl.esl.ultimate.wifirobot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainScreen extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Button connBtn;
    private EditText robotAddress;
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        final Context context = getApplicationContext();
        connBtn = (Button)findViewById(R.id.btnConn);
        robotAddress = (EditText)findViewById(R.id.ipAddr);
        robotAddress.setText("128.199.158.8");
        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = robotAddress.getText().toString();
                if(address.length()==0){
                    Toast toast = Toast.makeText(context, "Invalid Address", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                        //new testConnection().execute(address);
                    Intent conn = new Intent(getApplicationContext(), Connect.class);
                    conn.putExtra("inpAddress", address);
                    startActivity(conn);
                }

            }
        });
    }

    protected class testConnection extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... urls) {
            String parsedString="";
            try {

                URL url = new URL("http://"+urls[0]+":3000/test");
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
            Log.i("EIEI", parsedString);
            Log.i("EIEIGUM", String.valueOf(parsedString.compareTo("PASS")));
            if(parsedString.compareTo("PASS")==1){
                Intent conn = new Intent(getApplicationContext(), Connect.class);
                conn.putExtra("inpAddress", address);
                startActivity(conn);
                Log.i("EIEI2", address);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
            Intent conn = new Intent(getApplicationContext(), Connect.class);
            startActivity(conn);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainScreen Page", // TODO: Define a title for the content shown.
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
                "MainScreen Page", // TODO: Define a title for the content shown.
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
