package com.ibrahimrecepserpici.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
        fun createNetworkClient(baseUrl:String) = retrofitClient(baseUrl)

        private fun retrofitClient(baseUrl: String) : Retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()

}