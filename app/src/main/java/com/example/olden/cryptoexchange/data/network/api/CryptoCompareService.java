package com.example.olden.cryptoexchange.data.network.api;

import android.support.annotation.NonNull;

import com.example.olden.cryptoexchange.data.network.models.response.CoinsData;
import com.example.olden.cryptoexchange.data.network.models.response.PricesData;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoCompareService {

    @GET("/coinlist") @NonNull
    Single<CoinsData> getCoinsData();

    @GET("/price")
    Single<PricesData> getPrice(@Query("fsym") String from, @Query("tsym") List<String> to);

}
