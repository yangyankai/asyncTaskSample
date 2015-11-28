/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/9/15 4:56:14
 * Project: T_hander_update_UI
 * File: MainActivity.java
 * Class: MainActivity
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.mob.t_hander_update_ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener
{
	private TextView text;
	private Button   changeText;
	ProgressDialog progressDialog;



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		text = (TextView) findViewById(R.id.text);
////		ProgressDialog progressDialog=(ProgressDialog)findViewById(R.id.progress);
//
		progressDialog=new ProgressDialog(this);
		changeText = (Button) findViewById(R.id.change_text);
		changeText.setOnClickListener(this);
	}


	class DownloadTask extends AsyncTask<Void,Integer,Boolean>
	{
		@Override
		protected void onPreExecute()
		{
//			super.onPreExecute();
//			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
			progressDialog.show();

		}

		@Override
		protected Boolean doInBackground(Void... voids)
		{
			try
			{
				int downloadPercent=0;
				while (true){
//					int downloadPercent=doDownload();
					downloadPercent++;
					Thread.sleep(100);

					publishProgress(downloadPercent);
					if(downloadPercent>=100){
						break;
					}
				}
			}catch (Exception e){
				return false;
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			progressDialog.setMessage("Downloaded " + values[0] + "%");
		}



		//  result 的取值为   doInBackground   的返回值
		// UI 线程，可对UI进行更新
		@Override
		protected void onPostExecute(Boolean result)
		{
			progressDialog.dismiss();
			if(result){
				Toast.makeText(MainActivity.this,"download succeeded",Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(MainActivity.this,"download failed",Toast.LENGTH_SHORT).show();
			}
		}
	}





	@Override
	public void onClick(View v)
	{
		Log.e("aaa","click");
		new DownloadTask().execute();
	}
}
