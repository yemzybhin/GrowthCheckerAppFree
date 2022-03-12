package ade.yemi.moreapps.Network

import ade.yemi.moreapps.models.AllAppDetails
import retrofit2.Call
import retrofit2.http.GET

//private const val BASE_URL = "https://facts-guru.herokuapp.com/api/v1/apps/get"

interface  RetrofitInterface{
    @get:GET("api/v1/apps/get")
    val post: Call<AllAppDetails?>?

    companion object{
        const val BASE_URL = "https://facts-guru.herokuapp.com"
    }

}
