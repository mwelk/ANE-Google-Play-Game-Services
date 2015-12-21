package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.freshplanet.googleplaygames.Extension;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;

/**
 * Created by renaud on 09/09/2014.
 */
public class AirGooglePlayGamesGetAuthTokenFunction implements FREFunction {

    @Override
    public FREObject call(FREContext arg0, FREObject[] arg1) {

        final android.app.Activity activity = arg0.getActivity();
        Extension.context.createHelperIfNeeded(activity);

        String accountName = null;
        try
        {
            accountName = arg1[0].getAsString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        //scope in form of audience:server:client_id:X
        String scope = null;
        try
        {
            scope = arg1[1].getAsString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        final String token;
        try {
            token = GoogleAuthUtil.getToken(activity, accountName, scope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (GoogleAuthException e) {
            e.printStackTrace();
            return null;
        }

        if (token != null)
        {
            try {
                return FREObject.newObject(token);
            } catch (FREWrongThreadException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}