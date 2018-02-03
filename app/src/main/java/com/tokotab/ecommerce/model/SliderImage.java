package com.tokotab.ecommerce.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class SliderImage {
    private String InternalID, SliderName, SliderPicture, SliderLink;

    public SliderImage(String internalID, String sliderName, String sliderPicture, String sliderLink) {
        InternalID = internalID;
        SliderName = sliderName;
        SliderPicture = sliderPicture;
        SliderLink = sliderLink;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getSliderName() {
        return SliderName;
    }

    public void setSliderName(String sliderName) {
        SliderName = sliderName;
    }

    public String getSliderPicture() {
        return SliderPicture;
    }

    public void setSliderPicture(String sliderPicture) {
        SliderPicture = sliderPicture;
    }

    public String getSliderLink() {
        return SliderLink;
    }

    public void setSliderLink(String sliderLink) {
        SliderLink = sliderLink;
    }

    public void setImage(ImageView imgvView){
        String img=getSliderPicture().replaceAll(" ", "%20");
        new DownloadImageTask(imgvView)
                .execute("http://home.stationeryone.com"+img);
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
