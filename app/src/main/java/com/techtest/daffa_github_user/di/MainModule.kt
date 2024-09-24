package com.techtest.daffa_github_user.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.techtest.daffa_github_user.BuildConfig
import com.techtest.daffa_github_user.data.UserRepository
import com.techtest.daffa_github_user.data.source.local.LocalDataSource
import com.techtest.daffa_github_user.data.source.local.room.Config.DATABASE_NAME
import com.techtest.daffa_github_user.data.source.local.room.UserDatabase
import com.techtest.daffa_github_user.data.source.remote.RemoteDataSource
import com.techtest.daffa_github_user.data.source.remote.network.ApiService
import com.techtest.daffa_github_user.domain.repository.IUserRepository
import com.techtest.daffa_github_user.domain.usecase.UserInteractor
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import com.techtest.daffa_github_user.ui.detail.DetailUserViewModel
import com.techtest.daffa_github_user.ui.home.HomeViewModel
import com.techtest.daffa_github_user.ui.search.SearchViewModel
import com.techtest.daffa_github_user.util.DataMapper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


const val CONNECTION_TIMEOUT = 120L
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val networkModule = module {
    single {
        val context: Context = get()
        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}