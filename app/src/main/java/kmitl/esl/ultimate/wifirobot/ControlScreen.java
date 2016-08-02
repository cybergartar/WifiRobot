package kmitl.esl.ultimate.wifirobot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ControlScreen extends AppCompatActivity {
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_main_screen);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            address = extras.getString("inpAddress");
        }
        Button upBtn = (Button)findViewById(R.id.upBtn);
        Button downBtn = (Button)findViewById(R.id.downBtn);
        Button leftBtn = (Button)findViewById(R.id.leftBtn);
        Button rightBtn = (Button)findViewById(R.id.rightBtn);

        Button cptBtn = (Button)findViewById(R.id.cptBtn);

        final String finalAddress = address;
        final String file_url = "http://" + finalAddress + ":1381/capture";

        upBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":1381/connect/up/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":1381/connect/up/0"); break;
                }
                return true;
            }
        });
        downBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":1381/connect/down/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":1381/connect/down/0"); break;
                }
                return true;
            }
        });
        leftBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":1381/connect/left/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":1381/connect/left/0"); break;
                }
                return true;
            }
        });
        rightBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: new httpConnection().execute("http://"+ finalAddress +":1381/connect/right/1");   break;
                    case MotionEvent.ACTION_UP: new httpConnection().execute("http://"+ finalAddress +":1381/connect/right/0"); break;
                }
                return true;
            }
        });

        cptBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN: new DownloadFileFromURL().execute(file_url);break;
                }
                return true;
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

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        // generate file name by timestamp
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        String filename = formattedDate;

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            new Toast(ControlScreen.this).makeText(ControlScreen.this, "Downloading...", Toast.LENGTH_SHORT);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/WifiRobotImages" + "/" + filename + ".jpg");

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            Toast complete = Toast.makeText(ControlScreen.this, "Captured image!\n" + "File name: " + filename + ".jpg", Toast.LENGTH_SHORT);
            complete.show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
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
            new httpConnection().execute("http://"+ address +":1381/test");
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
                            new httpConnection().execute("http://" + address + ":1381/shutdown");
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
    }

}
