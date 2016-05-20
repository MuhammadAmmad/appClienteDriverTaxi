package com.nucleosis.www.appdrivertaxibigway.ConexionRed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;

public class ConnectionUtils {
	private ConnectionUtils() {
	}

	public static boolean isNetworkConnected( Context oContext ) {
		try {
			return isWifiConnected( oContext ) || isMobileConnected( oContext );
		}
		catch ( Exception e ) {
			return true;
		}
	}
	@SuppressWarnings("deprecation")
	public static boolean isWifiConnected( Context context ) {
		ConnectivityManager connManager = ( ConnectivityManager ) context
				.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo netInfo = connManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
		return ( ( netInfo != null ) && netInfo.isConnected() );

	}

	public boolean isInternet( ) {
		final boolean[] conecion = {false};
		new AsyncTask<String, String, Boolean>() {
			boolean conex=false;
			@Override
			protected Boolean doInBackground(String... params) {
				Runtime runtime = Runtime.getRuntime();
				try {
					Process mIpAddrProcess = runtime.exec( "/system/bin/ping -c 1 8.8.8.8" );
					int mExitValue = mIpAddrProcess.waitFor();
					if ( mExitValue == 0 ) {
						conex= true;
					}
					else {
						conex= false;
					}
				}
				catch ( InterruptedException ignore ) {
					ignore.printStackTrace();
					conex=false;
				}
				catch ( IOException e ) {
					e.printStackTrace();
					conex=false;
				}

				return conex;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				conecion[0] =aBoolean;
			}
		}.execute();


		return conecion[0];

	}
	@SuppressWarnings("deprecation")
	public static boolean isMobileConnected( Context context ) {
		ConnectivityManager connManager = ( ConnectivityManager ) context
				.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo netInfo = connManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
		return ( ( netInfo != null ) && netInfo.isConnected() );
	}
}
