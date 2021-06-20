package com.dell.statusdownloader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dell.statusdownloader.Model.WhatsappStatusModel;
import com.dell.statusdownloader.R;
import com.dell.statusdownloader.Util;
import com.dell.statusdownloader.databinding.WhatsappItemLayoutBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.apache.commons.io.FileUtils.copyFileToDirectory;

public class WhatsappAdapter extends RecyclerView.Adapter<WhatsappAdapter.ViewHolder> {

    private ArrayList<WhatsappStatusModel> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public WhatsappAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private  String saveFilePath= String.valueOf(Util.RootDirecWhatsapp);
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater =LayoutInflater.from(parent.getContext());

        }
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.whatsapp_item_layout,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhatsappStatusModel item=list.get(position);
        if (item.getUri().toString().endsWith(".mp4")){
            holder.binding.playButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.playButton.setVisibility(View.GONE);
        }

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.binding.download.setOnClickListener(v->{
            Util.createFileFolder();
            final String path=item.getPath();
            final File file=new File(path);
            File destFile=new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file,destFile);
                Toast.makeText(context,"Save To:"+file,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,"Error:"+e.getMessage()+"\n",Toast.LENGTH_SHORT).show();

            }

            Toast.makeText(context,"Save To:"+saveFilePath,Toast.LENGTH_SHORT).show();


        });
    }
    private static void copy(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            WhatsappItemLayoutBinding binding;
        public ViewHolder(@NonNull WhatsappItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}

