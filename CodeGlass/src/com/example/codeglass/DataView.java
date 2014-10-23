package com.example.codeglass;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;


public class DataView extends TextView {

	private String server = "128.195.4.195";
	private String port = "8000";
	private String connection = "connection.php";
	private String urlName = "http://" + server + ":" + port + "/" + connection;
	private String username;
	private String lineNumber;
	private String fileName;
	private String projectName;
	
	private BufferedReader reader;
	private StringBuilder builder;
	
	
	public DataView(Context context) throws IOException, JSONException {
		super(context);
		ConnectionTask connectionTask = new ConnectionTask();
		connectionTask.execute();
	}
	
	public void update() {
		ConnectionTask connectionTask = new ConnectionTask();
		connectionTask.execute();
	}
	
//---------------------------------------------------------------------------------

	private class ConnectionTask extends AsyncTask<Void, Void, Void> {

		private JSONObject glassData;
		private String myUsername = "Lee";

		@Override
		protected Void doInBackground(Void...params) {
			
			try {
				URL url = new URL(urlName);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				try {
					InputStream stream = new BufferedInputStream(conn.getInputStream());
					
					builder = new StringBuilder();
					reader = new BufferedReader(new InputStreamReader(stream));
					
					String line;
					try {
						while((line = reader.readLine()) != null) {
							builder.append(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					try {
						glassData = new JSONObject(builder.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}	
					
				} finally {
		            conn.disconnect();
		        }
				
				JSONArray userData = glassData.getJSONArray("data").getJSONObject(0).getJSONArray("users");
				JSONObject user = null;
				for (int i = 0; i < userData.length(); i++) {
					if (!myUsername.equals(userData.getJSONObject(i).getString("username"))) 
						user = userData.getJSONObject(i);
				}
				
				if (user != null) {
					username = user.getString("username");
					lineNumber = user.getString("line_number");
					fileName = user.getString("file_name");
					projectName = user.getString("project_name");
				}
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			String content;
			content = username + " is on line " + lineNumber + " in the file " + fileName + " in " + projectName;
			setText(content);
		}
	}
}
