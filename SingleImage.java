package kmitl.esl.ultimate.wifirobot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class SingleImage extends AppCompatActivity {

    ImageView imageView;
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

        // Set the decoded bitmap into ImageView
        imageView.setImageBitmap(bmp);
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
