package kmitl.esl.ultimate.wifirobot;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class LoginScreen extends AppCompatActivity {

    private Button connBtn;
    private EditText robotAddress;
    String address = "";
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
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
                    Intent conn = new Intent(context, ControlScreen.class);
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
                Intent conn = new Intent(context, ControlScreen.class);
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
            Intent conn = new Intent(getApplicationContext(), ControlScreen.class);
            startActivity(conn);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
