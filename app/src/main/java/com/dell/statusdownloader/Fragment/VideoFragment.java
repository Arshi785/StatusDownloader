package com.dell.statusdownloader.Fragment;

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
import com.dell.statusdownloader.databinding.FragmentVideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding videoBinding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        videoBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_video,container,false);


        list=new ArrayList<>();
        getData();
        videoBinding.videRefresh.setOnRefreshListener(()->{
            list=new ArrayList<>();
            getData();
            videoBinding.videRefresh.setRefreshing(false);
        });



        return videoBinding.getRoot();

    }

    private void getData() {
        WhatsappStatusModel model;
        String targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/.Statuses";
        File targetDirector= new File(targetPath);
        File[] allFilels=targetDirector.listFiles();

        String targetPathclo= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp_cloned/Media/.Statuses";
        File targetDirectorclo= new File(targetPathclo);
        File[] allFilelsclo=targetDirectorclo.listFiles();

        String targetPathBusiness= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp Business/Media/.Statuses";
        File targetDirectorBusiness= new File(targetPathBusiness);
        File[] allFilelsBusiness=targetDirectorBusiness.listFiles();



        String targetPathGB= Environment.getExternalStorageDirectory().getAbsolutePath()+"/GBWhatsApp/Media/.Statuses";
        File targetDirectorGB= new File(targetPathGB);
        File[] allFilelsGB=targetDirectorGB.listFiles();


        if (!(allFilels==null)) {
            Arrays.sort(allFilels, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFilels.length; i++) {
                File file = allFilels[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsappStatusModel("whats" + i,
                            Uri.fromFile(file),
                            allFilels[i].getAbsolutePath(),
                            file.getName());


                    list.add(model);
                }
            }
        }
        if (!(allFilelsBusiness==null)) {
            Arrays.sort(allFilelsBusiness, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFilelsBusiness.length; i++) {
                File file = allFilelsBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsappStatusModel("whats" + i,
                            Uri.fromFile(file),
                            allFilelsBusiness[i].getAbsolutePath(),
                            file.getName());


                    list.add(model);
                }
            }
        }
        if (!(allFilelsclo==null)) {
            Arrays.sort(allFilelsclo, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFilelsclo.length; i++) {
                File file = allFilelsclo[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsappStatusModel("whats" + i,
                            Uri.fromFile(file),
                            allFilelsclo[i].getAbsolutePath(),
                            file.getName());


                    list.add(model);
                }
            }
        }
        if (!(allFilelsGB==null)) {
            Arrays.sort(allFilelsGB, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFilelsGB.length; i++) {
                File file = allFilelsGB[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsappStatusModel("whatsGB" + i,
                            Uri.fromFile(file),
                            allFilelsGB[i].getAbsolutePath(),
                            file.getName());


                    list.add(model);
                }
            }
        }
        adapter=new WhatsappAdapter(list,getActivity());
        videoBinding.whatsapRecycler.setAdapter(adapter);

    }
}