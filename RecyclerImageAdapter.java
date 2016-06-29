package kmitl.esl.ultimate.wifirobot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder> {
    private Context mContext;
    private String[] filePath;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView preview;

        public ViewHolder(View itemView) {
            super(itemView);
            preview = (ImageView) itemView.findViewById(R.id.imagePreview);
        }
    }

    public RecyclerImageAdapter(Context context, String[] fileInput){
        mContext = context;
        filePath = fileInput;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_recycler, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerImageAdapter.ViewHolder holder, int position) {
        Bitmap bmp = BitmapFactory.decodeFile(filePath[position]);
        holder.preview.setImageBitmap(bmp);
    }


    @Override
    public int getItemCount() {
        if(filePath != null)
            return filePath.length;
        else
            return 0;
    }
}
