package com.nucleosis.www.appclientetaxibigway.ConexionRed;


import android.content.Context;

import java.io.IOException;


public final class ConnectionConfig {

    public ConnectionConfig() {
    }

/*    public static String getServerUrl( boolean hasInternet ) {
        return AppConfig.connection.getServerUrl( hasInternet );
    }*/

    public static int getConnectionTimeout() {
        return 100000;
    }

    public static int getSocketTimeout() {
        return 100000;
    }


    public static boolean isInternet( Context context ) {
        if(ConnectionUtils.isWifiConnected(context)){
           return ConnectionUtils.isInternet(context);
        }
        else if (ConnectionUtils.isMobileConnected(context)){
            return true;
        }
        return false;
    }


/*    public static boolean isConectionLocal( Context context ) {
        Runtime runtime = Runtime.getRuntime();
        String privateIP = AppConfig.connection.getPrivateServerUrl().split(":")[1].replace("//", "");
        try {
            Process ipProcess = runtime.exec( "/system/bin/ping -c 1 " + privateIP );
            int exitValue = ipProcess.waitFor();
            return ( exitValue == 0 );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        return false;

    }*/



}
