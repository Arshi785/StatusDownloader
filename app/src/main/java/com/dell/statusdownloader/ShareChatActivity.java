package com.dell.statusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dell.statusdownloader.MyDialog.MyDialogInfo;
import com.dell.statusdownloader.databinding.ActivityShareChatBinding;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class ShareChatActivity extends AppCompatActivity {
    private ActivityShareChatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding= DataBindingUtil.setContentView(this,R.layout.activity_share_chat);
    binding.sharechatDownBtn.setOnClickListener(v->{
        getShareChatData();
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
                binding.sharechatUrl.setText(pasteData);
            }
        });

        AdView adView = new AdView(this, "2359810640818550_2360621157404165", AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container_sharechat);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();


    }

    private void getShareChatData() {
        URL url= null;
        try {
            url = new URL(binding.sharechatUrl.getText().toString());
            String host=url.getHost();
            if(host.contains("sharechat"))
                new callGetShareChatData().execute(binding.sharechatUrl.getText().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void showDialogShare(View view) {
        MyDialogInfo dialog = new MyDialogInfo();
        dialog.show(getSupportFragmentManager(), MyDialogInfo.SHARE_DIALOG);
    }

    class callGetShareChatData extends AsyncTask<String,Void, Document>{
            Document scdocument;
        @Override
        protected Document doInBackground(String... strings) {
            try {
                scdocument= Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scdocument;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl=document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");

            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryShareChat,ShareChatActivity.this,"ShareChat "+System.currentTimeMillis()+".mp4");
        }
    }
}