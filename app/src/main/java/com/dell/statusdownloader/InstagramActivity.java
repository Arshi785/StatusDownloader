package com.dell.statusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dell.statusdownloader.API.ApiUtilities;
import com.dell.statusdownloader.Model.InstaModel;
import com.dell.statusdownloader.MyDialog.MyDialogInfo;
import com.dell.statusdownloader.databinding.ActivityInstagramBinding;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class InstagramActivity extends AppCompatActivity {
    private ActivityInstagramBinding binding;
    public String videoUrl,video;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_instagram);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Waiting...");
        progressDialog.setCancelable(false);

        binding.igDownBtn.setOnClickListener(v->{
            getVideo();
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
                binding.igUrl.setText(pasteData);
            }
        });




        AdView adView = new AdView(this, "2359810640818550_2360621857404095", AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container_insta);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

    }
    public void showDialog(View view) {

        MyDialogInfo dialog = new MyDialogInfo();
        dialog.show(getSupportFragmentManager(), MyDialogInfo.INSTA_DIALOG);
    }
    private void getVideo() {
        if(TextUtils.isEmpty(binding.igUrl.getText().toString())){
            Toast.makeText(this, "Please Paste Link", Toast.LENGTH_SHORT).show();
        }
        else {
            if(binding.igUrl.getText().toString().contains("instagram")){
                progressDialog.show();
                String replace;
                if(binding.igUrl.getText().toString().contains("?utm_source=ig_web_copy_link")){
                    replace="?utm_source=ig_web_copy_link";
                    video=binding.igUrl.getText().toString().replace(replace,"");
                }else if(binding.igUrl.getText().toString().contains("?utm_medium=copy_link")){
                    replace="?utm_medium=copy_link";
                    video=binding.igUrl.getText().toString().replace(replace,"");

                }else if(binding.igUrl.getText().toString().contains("?utm_medium=share_sheet")){
                    replace="?utm_medium=share_sheet";
                    video=binding.igUrl.getText().toString().replace(replace,"");

                }  else
                    video=binding.igUrl.getText().toString();
              //  Toast.makeText(this, ""+video, Toast.LENGTH_SHORT).show();
                ApiUtilities.getApiInterface().getInfo(video).enqueue(new Callback<InstaModel>() {
                    @Override
                    public void onResponse(Call<InstaModel> call, Response<InstaModel> response) {
                        if (response.body()!=null) {
                            progressDialog.dismiss();
                            videoUrl=response.body().getInfo().get(0).getVideo_url();
                            Log.d("Instagram", "onResponse: " + response.body().getInfo().get(0).getVideo_url());
                            Util.download(videoUrl,Util.RootDirectoryInstagram,InstagramActivity.this ,"Instagram_"+System.currentTimeMillis()+".mp4");

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(InstagramActivity.this, "Response is null:Please Use Video Url", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InstaModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Instagram", "onFailure: "+t.getMessage());
                        Toast.makeText(InstagramActivity.this, "Insta"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }else
            {
                Toast.makeText(this, "Please Provide instaUrl ", Toast.LENGTH_SHORT).show();
            }

        }

    }
}