package kmitl.esl.ultimate.wifirobot;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class ViewImages extends AppCompatActivity {

    private String[] FilePathStrings;
    private File[] listFile;
    File file;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int fileCount;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_images);
//
//        filePrepare();
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.gridImage);
//        if(mRecyclerView != null)
//            mRecyclerView.setHasFixedSize(false);
//
//        mLayoutManager = new GridLayoutManager(this, 3);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mAdapter = new RecyclerImageAdapter(this, FilePathStrings);
//        mRecyclerView.setAdapter(mAdapter);
//
//        Log.i("EIEI", (mAdapter.getItemCount()+""));
        filePrepare();
        fileCount = listFile.length;

    }

    public void onStart() {
        super.onStart();
    }

    public void onResume(){
        super.onResume();
        int newFileCount;

        setContentView(R.layout.view_images);

        filePrepare();
        newFileCount = listFile.length;

        if(newFileCount != fileCount){
            Toast delete = Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT);
            delete.show();
        }

        fileCount = newFileCount;

        mRecyclerView = (RecyclerView) findViewById(R.id.gridImage);
        if(mRecyclerView != null)
            mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerImageAdapter(this, FilePathStrings);
        mRecyclerView.setAdapter(mAdapter);

        Log.i("EIEI", (mAdapter.getItemCount()+""));

    }

    private void filePrepare(){
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "WifiRobotImages");
        if(!file.exists())
            file.mkdirs();

        if(file.isDirectory()){
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            for(int i=0; i<listFile.length; i++){
                FilePathStrings[i] = listFile[i].getAbsolutePath();
            }
        }
    }

    @Override
    public void onStop() {
        mRecyclerView.destroyDrawingCache();
        super.onStop();
    }



}
