package com.dell.statusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dell.statusdownloader.MyDialog.MyDialogInfo;
import com.dell.statusdownloader.databinding.ActivityFbBinding;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;


public class FbActivity extends AppCompatActivity {

    private ActivityFbBinding binding;
    private FbActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_fb);
        activity=this;

        binding.downloadBtn.setOnClickListener(v->{
            getFacebookData();
        });
        binding.paste.setOnClickListener(v->{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String pasteData = "";

            // If it does contain data, decide if you can handle the data.
            if (!(clipboard.hasPrimaryClip())) {

            } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                // since the clipboard has data but it is not plain text

            } else {

                //since the clipboard contains plain text.
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                // Gets the clipboard as text.
                pasteData = item.getText().toString();
                binding.fbUrl.setText(pasteData);
            }
        });

        AdView adView = new AdView(this, "2359810640818550_2360615744071373", AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container1);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();


    }

    private void getFacebookData() {
        URL url= null;
        try {
            url = new URL(binding.fbUrl.getText().toString());
            String host=url.getHost();
            if (host.contains("facebook.com")){
                new CallGetFbData().execute(binding.fbUrl.getText().toString());

            }else Toast.makeText(activity, "Url is InValid", Toast.LENGTH_SHORT).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void showDialog(View view) {
        MyDialogInfo dialog = new MyDialogInfo();
        dialog.show(getSupportFragmentManager(), MyDialogInfo.FB_DIALOG);
    }

    class CallGetFbData extends AsyncTask<String,Void, Document>{
            Document fbDDoc;
        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDDoc= Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fbDDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl=document.select("meta[property=\"og:video\"]").last().attr("content");

            if (!videoUrl.equals("")){
                Util.download(videoUrl, Util.RootDirectoryFaceBook  ,activity,"facebook"+System.currentTimeMillis()+".mp4");
            }

        }
    }


    private void showDialog() {

     //   dialog.setListener();

    }
}