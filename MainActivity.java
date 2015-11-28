package com.ykai.asncytasktest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by yangyankai on 2015/11/28.
 */
public class ProgressActivity extends Activity implements View.OnClickListener {
    private Button changeText;
    ProgressDialog progressDialog;

    DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_progress);

        progressDialog = new ProgressDialog(this);
        changeText = (Button) findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }


    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                int downloadPercent = 0;
                while (true) {
//                    if(isCancelled())
//                    {
//                        Log.e("yyk","while cancle");
//                        break;
//                    }
                    downloadPercent++;
                    Thread.sleep(100);
                    publishProgress(downloadPercent);
                    if (downloadPercent >= 100) {
                        break;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            if(isCancelled())
//            {
//                Log.e("yyk","progress return");
//                return;
//            }
            progressDialog.setMessage("Downloaded " + values[0] + "%");
        }


        //  result 的取值为   doInBackground   的返回值
        // UI 线程，可对UI进行更新
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                Toast.makeText(ProgressActivity.this, "download succeeded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProgressActivity.this, "download failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //取消正在加载中的异步任务
    @Override
    protected void onPause() {
        super.onPause();
        if (downloadTask != null && downloadTask.getStatus() == AsyncTask.Status.RUNNING) {
            //cancel 方法只是将对应asynctask标记为cancel的状态，并不是真正的取消
            Log.e("yyk","pause");
            downloadTask.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        downloadTask = new DownloadTask();
        downloadTask.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("yyk","back");
    }
}
