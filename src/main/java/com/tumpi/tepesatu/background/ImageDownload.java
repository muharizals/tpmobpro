package com.tumpi.tepesatu.background;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tumpi.tepesatu.view.RingProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by muharizals on 18/09/2016.
 */
public class ImageDownload extends AsyncTask<String,Integer,Bitmap>{

    RingProgressBar ringProgressBar;
    ImageView imageView;
    int prog;

    public ImageDownload(RingProgressBar ringProgressBar,ImageView imageView){
        this.ringProgressBar = ringProgressBar;
        this.imageView = imageView;
    }

    public void onPreExecute(){
        super.onPreExecute();
        prog = 0;
        ringProgressBar.setProgress(0);
    }
    public Bitmap doInBackground(String... params){

        try{
            URL url = new URL(params[0]);
            HttpsURLConnection httpcon = (HttpsURLConnection) url.openConnection();

            httpcon.connect();
           // int lengOfImage = httpcon.getContentLength();

            InputStream is = httpcon.getInputStream();


            //gak pake ini juga gpp,ini cuman pengen liat aja asycntask kerjanya gimana,ini lebih baik dihilangkan
            while (prog < 100){
                prog +=2;
                publishProgress(prog);
                SystemClock.sleep(300);
            }

            return BitmapFactory.decodeStream(is);

        }catch (Exception e){
            Log.e("Image","Failed to load image",e);
        }
        return null;
    }

    public void onProgressUpdate(Integer... params){
        super.onProgressUpdate(params);
        ringProgressBar.setProgress(params[0]);
    }

    public void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        ringProgressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
    }
}
