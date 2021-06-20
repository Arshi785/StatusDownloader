package com.dell.statusdownloader.Fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dell.statusdownloader.Adapter.WhatsappAdapter;
import com.dell.statusdownloader.Model.WhatsappStatusModel;
import com.dell.statusdownloader.R;
import com.dell.statusdownloader.databinding.FragmentImageBinding;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ImageFragment extends Fragment {
private FragmentImageBinding imageBinding;
private ArrayList<WhatsappStatusModel> list;
private WhatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            imageBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_image,container,false);


            list=new ArrayList<>();
            getData();
             imageBinding.refresh.setOnRefreshListener(()->{
                list=new ArrayList<>();
                getData();
                imageBinding.refresh.setRefreshing(false);
            });

            return imageBinding.getRoot();
    }

    private void getData() {
        WhatsappStatusModel model;
    String targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/.Statuses";
        File targetDirector= new File(targetPath);
        File[] allFilels=targetDirector.listFiles();


        //WHATSAPP-I
        Arrays.sort(allFilels, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.compare(o2.lastModified(), o1.lastModified());
            }
        });


        for (int i=0;i<allFilels.length;i++) {
            File file = allFilels[i];
            if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                model = new WhatsappStatusModel("whats" + i,
                        Uri.fromFile(file),
                        allFilels[i].getAbsolutePath(),
                        file.getName());


                list.add(model);
            }
        }


        String targetPathwa2= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp_cloned/Media/.Statuses";
        File targetDirectorclo= new File(targetPathwa2);
        File[] allFilelsclo=targetDirectorclo.listFiles();

        if (!(allFilelsclo==null)) {
            //WHATSAPP-II
            Arrays.sort(allFilelsclo, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });


        for (int i=0;i<allFilelsclo.length;i++) {
            File file = allFilelsclo[i];
            if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                model = new WhatsappStatusModel("whats" + i,
                        Uri.fromFile(file),
                        allFilelsclo[i].getAbsolutePath(),
                        file.getName());


                list.add(model);
            }
        }
        }



        String targetPathBusiness= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp Business/Media/.Statuses";
        File targetDirectorBusiness= new File(targetPathBusiness);
        File[] allFilelsBusiness=targetDirectorBusiness.listFiles();

            //WHATSAPP-Business
        if (!(allFilelsBusiness==null)) {
            Arrays.sort(allFilelsBusiness, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });


        for (int i=0;i<allFilelsBusiness.length;i++) {
            File file = allFilelsBusiness[i];
            if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                model = new WhatsappStatusModel("whats" + i,
                        Uri.fromFile(file),
                        allFilelsBusiness[i].getAbsolutePath(),
                        file.getName());


                list.add(model);
            }    }
        }


        String targetPathGB= Environment.getExternalStorageDirectory().getAbsolutePath()+"/GBWhatsApp/Media/.Statuses";
        File targetDirectorGB= new File(targetPathGB);
        File[] files=targetDirectorGB.listFiles();

        //GB WHatsApp
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.compare(o2.lastModified(), o1.lastModified());
            }
        });


for (int i=0;i<files.length;i++) {
    File file = files[i];
    if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
        model = new WhatsappStatusModel("whats" + i,
                Uri.fromFile(file),
                files[i].getAbsolutePath(),
                file.getName());


        list.add(model);
    }
}





//Arrays.sort(allFilelsclo,((o1, o2) -> {
//            if(o1.lastModified()>o2.lastModified()){
//                return -1;
//            }
//            else if (o1.lastModified()<o2.lastModified())  return +1;
//            else return 0;
//        }));

//for (int i=0;i<allFilelsclo.length;i++) {
//    File file = allFilelsclo[i];
//    if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
//        model = new WhatsappStatusModel("whats" + i,
//                Uri.fromFile(file),
//                allFilelsclo[i].getAbsolutePath(),
//                file.getName());
//
//
//        list.add(model);
//    }
//}

//        Arrays.sort(allFilelsGB,((o1, o2) -> {
//            if(o1.lastModified()>o2.lastModified()){
//                return -1;
//            }
//            else if (o1.lastModified()<o2.lastModified())  return +1;
//            else return 0;
//        }));
//
//for (int i=0;i<allFilelsGB.length;i++){
//    File file=allFilelsGB[i];
//    if (Uri.fromFile(file).toString().endsWith(".png")||Uri.fromFile(file).toString().endsWith(".jpg")){
//        model=new WhatsappStatusModel("whatsGB" +i,
//                Uri.fromFile(file),
//                allFilelsGB[i].getAbsolutePath(),
//                file.getName());
//        list.add(model);
//    }
//}

adapter=new WhatsappAdapter(list,getActivity());
imageBinding.whatsapRecycler.setAdapter(adapter);

    }
}