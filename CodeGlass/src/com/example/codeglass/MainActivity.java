package com.example.codeglass;

import java.io.IOException;

import org.json.JSONException;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	private DataView dataView;
	private Runnable refresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			dataView = new DataView(this);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		dataView.setTextSize(40);
		setContentView(dataView);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		final Handler handler = new Handler();
		refresh = new Runnable() {
		    public void run() {
		        dataView.update();
		        handler.postDelayed(refresh, 1000);
		    }
		};
		handler.post(refresh);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
