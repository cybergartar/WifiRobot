package kmitl.esl.ultimate.wifirobot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class LoginScreen extends AppCompatActivity {

    private Button connBtn;
    private AutoCompleteTextView robotAddress;
    String address = "";
    final Context context = this;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        connBtn = (Button)findViewById(R.id.btnConn);

        sp = getSharedPreferences("allIPs", Context.MODE_PRIVATE);
        editor = sp.edit();
        final Set<String> nothing = new HashSet<String>();
        nothing.add("");
        final Set<String> ip = sp.getStringSet("ips", nothing);
        String[] ips = {};

        if(ip.size() != 0){
            ips = ip.toArray(new String[ip.size()]);
        }

        Log.i("IPS", Arrays.toString(ips));

        robotAddress = (AutoCompleteTextView) findViewById(R.id.ipAddr);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ips);

        robotAddress.setAdapter(adapter);
        robotAddress.setThreshold(1);

        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = robotAddress.getText().toString();
                if(address.length()==0){
                    Toast toast = Toast.makeText(context, "Invalid Address", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    if(!ip.contains(address)){
                        Log.i("ADD ADDRESS", address);
                        editor.clear();
                        ip.add(address);
                        editor.putStringSet("ips", ip);
                        if(editor.commit()){
                            Log.i("ADDRESULT", "OK" + ip.size());
                        }
                        else{
                            Log.i("ADDRESULT", "FAILED"+ ip.size());
                        }
                    }
                    new testConnection().execute(address);
                }

            }
        });
    }

    protected class testConnection extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... urls) {
            String parsedString="";
            try {

                URL url = new URL("http://"+urls[0]+":1381/test");
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
