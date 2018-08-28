package com.mock.fathi.supportrooster.tools;

import com.mock.fathi.supportrooster.models.Employees;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallbacks {
    private RetrofitEndpoints retroInterface = RetrofitClient.getRetroClient()
                                                             .create(RetrofitEndpoints.class);

    public void getEngineersList(final RetrofitWrapper cbWrapper) {
        Call<Employees> registerCall = retroInterface.getEngineersList();
        registerCall.enqueue(new Callback<Employees>() {
            @Override
            public void onResponse(Call<Employees> call, Response<Employees> response) {
                if(response.isSuccessful()) {
                    Employees emp = response.body();
                    cbWrapper.success(emp);
                }
            }

            @Override
            public void onFailure(Call<Employees> call, Throwable t) {
                call.cancel();
                cbWrapper.failure("There's an error connecting to server.");
            }
        });
    }
}