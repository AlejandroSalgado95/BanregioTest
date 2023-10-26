package com.example.banregio_interview.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.banregio_interview.R
import com.example.banregio_interview.networking.ApiBanregio
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): ApiBanregio {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(ApiBanregio.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()
            .create(ApiBanregio::class.java)
    }


    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24)
        )
    }

}