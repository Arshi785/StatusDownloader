package com.dell.statusdownloader.MyDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dell.statusdownloader.R;

public class MyDialogInfo extends DialogFragment {
    public static final String FB_DIALOG="fbClass";
    public static final String INSTA_DIALOG="instaClass";
    public static final String SHARE_DIALOG="shareClass";
    String text;
    private DialogInterface.OnClickListener listener;

//  //  public MyDialogInfo(String text) {
//        this.text = text;
//    }

    public MyDialogInfo() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if(getTag().equals(FB_DIALOG))dialog=getFBInfoDialog();
        if(getTag().equals(INSTA_DIALOG))dialog = getInstaInfoDialog();
        if(getTag().equals(SHARE_DIALOG))dialog = getShareChatInfoDialog();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }
    public void setListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

    private Dialog getFBInfoDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_info, null);
        builder.setView(view);


        Button ok=view.findViewById(R.id.ok_btn);
            ok.setOnClickListener(v->dismiss());



        TextView title = view.findViewById(R.id.info);
        title.setText("1. Copy the video link from Facebook. \n2. Open the Status Downloader App\n3. Go to Insta Tab\n4. Click On Paste Button \n5. Now Click On Download Button.\n");
        return builder.create();
    }
    private Dialog getInstaInfoDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_info, null);
        builder.setView(view);

        Button ok=view.findViewById(R.id.ok_btn);
        ok.setOnClickListener(v->dismiss());

        TextView title = view.findViewById(R.id.info);
        title.setText("1. Copy or Share video link from Insta . \n2. Open the Status Downloader App\n3. Go to Insta Tab\n4. Click On Paste Button \n5. Now Click On Download Button.\n");
        return builder.create();
    }private Dialog getShareChatInfoDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_info, null);
        builder.setView(view);


        Button ok=view.findViewById(R.id.ok_btn);
        ok.setOnClickListener(v->dismiss());

        TextView title = view.findViewById(R.id.info);
        title.setText("1. Copy the video link from Sharechat. \n 2. Open the Status Downloader App\n3. Go to Sharechat Tab\n4. Click On Paste Button \n5. Now Click On Download Button.\n");
        return builder.create();
    }
}
