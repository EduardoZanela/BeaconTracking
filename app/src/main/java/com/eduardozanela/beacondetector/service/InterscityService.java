package com.eduardozanela.beacondetector.service;

import android.util.Log;

import com.eduardozanela.beacondetector.model.InterscityResourceDataRequest;
import com.eduardozanela.beacondetector.model.InterscityResourceDataResponse;
import com.eduardozanela.beacondetector.model.InterscityResourceResponse;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterscityService {

    public static final String INTERSCITY_URL = "http://playground.interscity.org";
    public static final String INTERSCITY_ERROR_TAG = "INTERSCITY-ERROR";
    private static InterscityService instancia;
    private Retrofit retrofit;
    private InterscityServiceInterface service;

    private InterscityService() {
        retrofit = new Retrofit.Builder().baseUrl(INTERSCITY_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(InterscityServiceInterface.class);
    }

    public static synchronized InterscityService getInstance(){
        if(instancia == null){
            instancia = new InterscityService();
        }
        return instancia;
    }

    public InterscityResourceDataResponse postResourceData(String uuid, InterscityResourceDataRequest request){
        try {
            return service.postResourceData(uuid, request).execute().body();
        } catch (IOException e) {
            Log.e(INTERSCITY_ERROR_TAG, "Error to post data to interscity " + e.getMessage(), e);
        }
        return new InterscityResourceDataResponse();
    }

    public InterscityResourceResponse postResource(InterscityResourceRequest request){
        try {
            return service.postResource(request).execute().body();
        } catch (IOException e) {
            Log.e(INTERSCITY_ERROR_TAG, "Error to post resource to interscity " + e.getMessage(), e);
        }
        return new InterscityResourceResponse();
    }

}
