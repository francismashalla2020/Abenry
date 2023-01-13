package com.thinkbold.abenry.Utils;


import com.thinkbold.abenry.Retrofit.RetrofitClient;
import com.thinkbold.abenry.Retrofit.ZungukaAfricaAPI;


public class Common {
    public static final String BASE_URL = "https://thinkbold.africa/abenry/";

//    public static VideoInterface getAPI() {
//        return RetrofitClient.getClient(BASE_URL).create(VideoInterface.class);
//    }

    public static ZungukaAfricaAPI getAPIs() {
        return RetrofitClient.getClient(BASE_URL).create(ZungukaAfricaAPI.class);
    }


}
