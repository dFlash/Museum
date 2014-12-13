package com.onpu.museum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServerResponse extends Activity {

	
	Button nextElem;
	TextView catDesc, catName, elemName;
	
	ArrayList<Entity> responseList;
	 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.server_response);
	    
	    
	    nextElem = (Button) findViewById(R.id.butNextElem);
	    catDesc  = (TextView) findViewById(R.id.tvCatDesc);
	    catName  = (TextView) findViewById(R.id.tvCatName);
	    elemName = (TextView) findViewById(R.id.tvElemDesc);
	    
	   
	    
	    responseList = new ArrayList<>();

	    getResponse();
		//new DDD().execute();
	    
//	    

	    	    
	}

	private void getResponse() {

		HttpClient client = new DefaultHttpClient();

		HttpGet request = new HttpGet(
				"http://salty-dawn-9781.herokuapp.com/api/exhibits?limit=20");

		HttpResponse response;

		

		try {
			// получение ответа и парсинг его
			response = client.execute(request);

			
			HttpEntity entity = response.getEntity();

			if (entity != null) {

				String str = EntityUtils.toString(entity, "UTF-8");

				JSONParser parser = new JSONParser();
				Object resultObject = null;
				try {
					resultObject = parser.parse(str);
				} catch (ParseException ex) {
					Toast.makeText(getApplicationContext(),
							"ParseException in http request",
							Toast.LENGTH_SHORT).show();
				}

				
				// заполнение массива
				String name = "";
				String desc = "";
				String header = "";
				String type = "";

				if (resultObject instanceof JSONArray) {
					JSONArray array = (JSONArray) resultObject;
					for (Object object : array) {
						JSONObject obj = (JSONObject) object;
						name = (String) obj.get("name");
						desc = (String) obj.get("description");

						Object o = obj.get("content");
						if (o instanceof JSONArray) {
							JSONArray arrayO = (JSONArray) o;
							for (Object ob : arrayO) {
								JSONObject obj2 = (JSONObject) ob;
								header += obj2.get("header");
							}
						}

						responseList.add(new Entity(name, desc, header,
								type));
					}

				}
				
				Toast.makeText(getApplicationContext(), "≈сть соединение", Toast.LENGTH_LONG);

			}
			else
			{
				Toast.makeText(getApplicationContext(), "Ќет соединение", Toast.LENGTH_LONG);

			}


		} catch (ClientProtocolException e1) {
			Toast.makeText(getApplicationContext(),
					"Protokol in http request", Toast.LENGTH_SHORT).show();
		} catch (IOException e1) {
			Toast.makeText(getApplicationContext(),
					"IOException in http request", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception eee) {
			Log.i("ALLAH AKBAR ", eee.toString());
		}
		
	}
	
	
//	private class DDD extends AsyncTask {
//
//		@Override
//		protected Object doInBackground(Object... params) {
//			getResponse();
//			return null;
//		}
//
//	}
	
	
	private class Entity {
		String name;
		String desc;
		String header;
		String type;
		
		public Entity(String name, String desc,String header, String type) {
			this.desc=desc;
			this.name=name;
			this.header=header;
			this.type=type;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public String getHeader() {
			return header;
		}
		
		public String getType() {
			return type;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((desc == null) ? 0 : desc.hashCode());
			result = prime * result
					+ ((header == null) ? 0 : header.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Entity)) {
				return false;
			}
			Entity other = (Entity) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (desc == null) {
				if (other.desc != null) {
					return false;
				}
			} else if (!desc.equals(other.desc)) {
				return false;
			}
			if (header == null) {
				if (other.header != null) {
					return false;
				}
			} else if (!header.equals(other.header)) {
				return false;
			}
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		private ServerResponse getOuterType() {
			return ServerResponse.this;
		}


		
		
	}

}
