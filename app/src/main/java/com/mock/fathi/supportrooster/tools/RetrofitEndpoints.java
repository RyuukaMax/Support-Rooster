package com.mock.fathi.supportrooster.tools;

import com.mock.fathi.supportrooster.models.Employees;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitEndpoints {
    @GET("/api/v2/engineers")
    Call<Employees> getEngineersList();
}
