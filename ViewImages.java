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
import java.util.ArrayList;


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
    String IMGS[] = {
            "https://scontent.fbkk1-1.fna.fbcdn.net/hphotos-xpf1/v/t1.0-9/12799132_999473050132284_6324695365229718601_n.jpg?oh=43d7f431c50f001b79c5a6833e5eb0f3&oe=5798C2CE",
            "https://scontent.fbkk1-1.fna.fbcdn.net/hphotos-xlf1/v/t1.0-9/1927760_999385286807727_4935019582717207141_n.jpg?oh=41bc92ca32e908cff4ec93f713fbaf9c&oe=5766B9C7",
            "https://scontent.fbkk1-1.fna.fbcdn.net/hphotos-xft1/v/t1.0-9/12744175_10204946308052352_1413629545025496902_n.jpg?oh=50e0b6e6db0577899fbe983972c83e4a&oe=574B4DDC",
            "https://scontent.fbkk1-1.fna.fbcdn.net/hphotos-xpl1/v/t1.0-9/12718072_10204929888521874_3462539950105664708_n.jpg?oh=088f29fcdd0cae4f34d5d9189c79ce0e&oe=57619950"
    };
    ArrayList<ImageModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_images);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

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

        for (int i = 0; i < IMGS.length; i++) {
//  Adding images & title to POJO class and storing in Array (our data)
            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
            imageModel.setUrl(IMGS[i]);
            data.add(imageModel);
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
