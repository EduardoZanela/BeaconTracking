package com.eduardozanela.beacondetector.service;

import com.eduardozanela.beacondetector.model.InterscityResourceDataRequest;
import com.eduardozanela.beacondetector.model.InterscityResourceDataResponse;
import com.eduardozanela.beacondetector.model.InterscityResourceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterscityServiceInterface {

    @POST("/adaptor/resources")
    Call<InterscityResourceResponse> postResource(@Body InterscityResourceRequest request);

    @POST("/adaptor/resources/{uuid}/data")
    Call<InterscityResourceDataResponse> postResourceData(@Path("uuid") String uuid, @Body InterscityResourceDataRequest request);
}
