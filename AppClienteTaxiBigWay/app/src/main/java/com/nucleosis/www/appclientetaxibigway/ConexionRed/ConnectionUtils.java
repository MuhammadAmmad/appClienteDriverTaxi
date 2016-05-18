package com.nucleosis.www.appclientetaxibigway.ConexionRed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

public final class ConnectionUtils {
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

	public static boolean isInternet( Context context ) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process mIpAddrProcess = runtime.exec( "/system/bin/ping -c 1 8.8.8.8" );
			int mExitValue = mIpAddrProcess.waitFor();
			if ( mExitValue == 0 ) {
				return true;
			}
			else {
				return false;
			}
		}
		catch ( InterruptedException ignore ) {
			ignore.printStackTrace();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		return false;

	}
	@SuppressWarnings("deprecation")
	public static boolean isMobileConnected( Context context ) {
		ConnectivityManager connManager = ( ConnectivityManager ) context
				.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo netInfo = connManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
		return ( ( netInfo != null ) && netInfo.isConnected() );
	}
}
