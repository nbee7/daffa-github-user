package com.techtest.daffa_github_user.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.techtest.daffa_github_user.BuildConfig
import com.techtest.daffa_github_user.data.UserRepository
import com.techtest.daffa_github_user.data.source.local.LocalDataSource
import com.techtest.daffa_github_user.data.source.local.room.Config.DATABASE_NAME
import com.techtest.daffa_github_user.data.source.local.room.UserDatabase
import com.techtest.daffa_github_user.data.source.remote.RemoteDataSource
import com.techtest.daffa_github_user.data.source.remote.network.ApiService
import com.techtest.daffa_github_user.domain.repository.IUserRepository
import com.techtest.daffa_github_user.util.DataMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    factory { get<UserDatabase>().userDetailDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

const val CONNECTION_TIMEOUT = 120L
val moshi: Moshi = Moshi.Builder().build()

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val utilModule = module {
    single { DataMapper() }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single<IUserRepository> { UserRepository(get(), get(), get()) }
}