package de.thorstenwitt.mensaapp;

import java.io.IOException;

import de.thorstenwitt.mensaapp.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
	
	ImageView iv;
	ProgressBar pb;
	TextView tv;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		iv = (ImageView) findViewById(R.id.splashImage);
		pb = (ProgressBar) findViewById(R.id.splashProgress);
		tv = (TextView) findViewById(R.id.splashText);
		if(isOnline()) {
			parseTask pt = new parseTask();
			pt.execute("");
		}
		else {
			tv.setText("Keine Internetverbindung");
			pb.setVisibility(View.GONE);
			iv.setVisibility(View.GONE);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_splash, menu);
		return true;
	}


	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && 
	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
	
	private class parseTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
	    }
		@Override
		protected String doInBackground(String... params) {
			LunchParser lp = LunchParser.getInstance();
			try {
				lp.parse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return null;
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				Intent i = new Intent(getApplicationContext(), MenuActivity.class);
				startActivity(i);
				finish();

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
