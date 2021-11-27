package com.ibrahimrecepserpici.data.di

import com.ibrahimrecepserpici.data.network.Client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object{

        private val client = Client();

        @Provides
        @Singleton
        fun createRetrofit() = client.createNetworkClient("https://fake-poi-api.mytaxi.com/")

    }
}