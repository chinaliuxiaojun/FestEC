package com.lxj.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.lxj.latte.app.Latte;
import com.lxj.latte.net.callback.IRequest;
import com.lxj.latte.net.callback.ISuccess;
import com.lxj.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by lxj on 2018/6/13.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    //只穿seccess，剩下的方法自己扩展
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

//执行完后回到主线程的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        aotoInstallApk(file);
    }
//下载完成直接安装
    private void aotoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install=new Intent();
            //新建一个栈
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir= (String) objects[0];
        String extension= (String) objects[1];
        final ResponseBody body= (ResponseBody) objects[2];
        final String name= (String) objects[3];
        final InputStream is=body.byteStream();
        if(downloadDir==null||downloadDir.equals("")){
            downloadDir="down_loads";
        }
        if(extension==null||extension.equals("")){
            extension="";
        }
        if(name==null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }
}
