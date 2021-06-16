package cnns.com.example.kotlintestapp

import cnns.com.example.kotlintestapp.models.*
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("departements/{code}")
    suspend fun getDepartement(@Path("code") code : Int): Response<Departement>

}