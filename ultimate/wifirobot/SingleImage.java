package kmitl.esl.ultimate.wifirobot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class SingleImage extends AppCompatActivity {

    ImageView imageView;
    String file = "";
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        // Retrieve data from MainActivity on GridView item click
        Intent i = getIntent();

        // Get the position
        int position = i.getExtras().getInt("position");

        // Get String arrays FilePathStrings
        String[] filepath = i.getStringArrayExtra("filePath");

        // Locate the ImageView in view_image.xml
        imageView = (ImageView) findViewById(R.id.full_image_view);
        Log.i("POS", position+"");
        // Decode the filepath with BitmapFactory followed by the position

        Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);

        file = filepath[position];

        Log.i("FILE", file);
        // Set the decoded bitmap into ImageView
        imageView.setImageBitmap(bmp);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_another_app){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + file), "image/*");
            startActivity(intent);
        }
        else if (id == R.id.menu_delete_image){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Delete image")
                    .setMessage("Are you sure to delete this image?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File fileDelete = new File(file);
                            if(fileDelete.exists()) {
                                fileDelete.delete();
                            }
                            SingleImage.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
