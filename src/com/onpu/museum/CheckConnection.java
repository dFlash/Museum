package com.onpu.museum;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CheckConnection extends Activity {
	
	TextView viewConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_connection);
		
		viewConnection = (TextView) findViewById(R.id.tvCheckConn);
		try {
			String result = new CheckingTask().execute().get();
			viewConnection.setText(result);

		} catch (InterruptedException | ExecutionException e) {
			Log.e("App exception", e.toString());
		}

	}
	
	public String checkConnection() {

		String isConnection = "ERROR - No connection to server";

		HttpClient client = new DefaultHttpClient();

		String requestStr = getResources().getString(R.string.server_url)
				+ getResources().getString(R.string.request_path);

		HttpGet request = new HttpGet(requestStr);

		HttpResponse response;

		try {
			// получение ответа
			response = client.execute(request);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				isConnection = "Connection was created success";
			}

		} catch (ClientProtocolException e1) {
			Log.e("App exception", e1.toString());
		} catch (IOException e1) {
			Log.e("App exception", e1.toString());
		} catch (Exception eee) {
			Log.e("App exception", eee.toString());
		}

		return isConnection;

	}
	
	
	private class CheckingTask extends AsyncTask<Void, Void,String> {

		@Override
		protected String doInBackground(Void... params) {
			return checkConnection();
		}

	}
}
