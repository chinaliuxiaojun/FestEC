package com.lxj.latte.net.download;

import android.os.AsyncTask;

import com.lxj.latte.net.RestCreator;
import com.lxj.latte.net.callback.IError;
import com.lxj.latte.net.callback.IFailure;
import com.lxj.latte.net.callback.IRequest;
import com.lxj.latte.net.callback.ISuccess;

import java.net.FileNameMap;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxj on 2018/6/13.
 */

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DOR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           IRequest request,
                           String downloadDir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DOR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handleDownload(){
        if(REQUEST!=null){
            REQUEST.onRequsetStart();
        }
        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                      if(response.isSuccessful()){
                          final ResponseBody responseBody=response.body();
                          final SaveFileTask task=new SaveFileTask(REQUEST,SUCCESS);
                          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DOR,EXTENSION,response,NAME);
                          //注意要判断是否已经下载完成，否则文件下载不全
                          if(task.isCancelled()){
                              if(REQUEST!=null){
                                  REQUEST.onRequestEnd();
                              }
                          }
                      }else {
                          if (ERROR!=null){
                              ERROR.onError(response.code(),response.message());
                          }
                      }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
