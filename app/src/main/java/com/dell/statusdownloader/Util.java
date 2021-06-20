package com.dell.statusdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;

public class Util {

    public static String RootDirectoryFaceBook="/StatusDownloader/Facebook/";

    public static String RootDirectoryShareChat="/StatusDownloader/ShareChat/";

    public static String RootDirectoryInstagram="/StatusDownloader/Instagram/";

    public static File RootDirecWhatsapp=
            new File(Environment.getExternalStorageDirectory() +"/Download/StatusDownloader/Whatsapp");

    public static void createFileFolder(){
        if (!RootDirecWhatsapp.exists())
            RootDirecWhatsapp.mkdirs();


    }

    public  static  void download(String downloadPath, String destinationPath, Context context,String fileName){
        Toast.makeText(context, "Downloading Start", Toast.LENGTH_SHORT).show();
        Uri uri=Uri.parse(downloadPath);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,destinationPath+fileName);
        ((DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE)).enqueue(request);
    }

}
