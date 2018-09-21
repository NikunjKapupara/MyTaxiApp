package com.android.mytaxi.retrofit


import com.android.mytaxi.ws_models.GetAllVehiclesModel
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by N!K$ on 8/24/18.
 */
interface RetrofitApiService {

    /**
     * CREATE INSTANCE FOR API SERVICE
     */
    companion object {

        fun create(): RetrofitApiService {

            val httpClient = OkHttpClient.Builder()

            /// to print the App Log I added -> HttpLoggingInterceptor()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)

            /// to add the web service request header
            httpClient.addInterceptor { chain ->

                val original = chain.request()
                val builder = original.newBuilder()
                builder.addHeader("Accept", "application/json")
                builder.addHeader("Content-Type", "application/json; charset=utf-8").build()


                val request = builder.build()
                 chain.proceed(request)
            }


            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpConstants.BASE_URL)
                    .client(httpClient.build())
                    .build()

            return retrofit.create(RetrofitApiService::class.java)
        }
    }



                                        /**
                                         * My Taxi WEB SERVICES
                                         */


    /**
     * GET ALL VEHICLES
     * sample URL: https://fake-poi-api.mytaxi.com/?p1Lat=53.694865&p1Lon=9.757589&p2Lat=53.394655&p2Lon=10.099891
      */

    @GET("./")
    fun getAllVehicles(@Query ("p1Lat") sourceLat:Double,
                       @Query ("p1Lon") sourceLong:Double,
                       @Query ("p2Lat") destLat:Double,
                       @Query ("p2Lon") destLong:Double
                       ): Observable<GetAllVehiclesModel>


    /**
     * http://maps.googleapis.com/maps/api/geocode/json?latlng=53.52986927047966,9.816411165053662&sensor=true
     */
    /*@GET("/maps/api/geocode/json")
    fun getAddress(@Query ("latlng") latitude:Double, @Query ("") longitude:Double): Observable<>*/

}