package com.uteq.appmoviles.payphoneapi.services;

import com.uteq.appmoviles.payphoneapi.models.ConsultSaleResponse;
import com.uteq.appmoviles.payphoneapi.models.CreateSaleRequest;
import com.uteq.appmoviles.payphoneapi.models.CreateSaleResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SaleService {

    @POST("Sale")
    Call<CreateSaleResponse> createSale(@HeaderMap Map<String, String> headers,
                                        @Body CreateSaleRequest model);

    @GET("Sale/{id}")
    Call<ConsultSaleResponse> getSale(@HeaderMap Map<String, String> headers,
                                      @Path("id") long id);
}
