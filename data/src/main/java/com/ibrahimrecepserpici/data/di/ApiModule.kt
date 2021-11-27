package com.ibrahimrecepserpici.data.di

import com.ibrahimrecepserpici.data.api.MyTaxiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideMyTaxiApi(retrofit: Retrofit): MyTaxiApi{
        return retrofit.create(MyTaxiApi::class.java)
    }
}