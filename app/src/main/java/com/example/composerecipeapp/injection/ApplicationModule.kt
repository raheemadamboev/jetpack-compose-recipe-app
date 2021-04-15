package com.example.composerecipeapp.injection

import android.content.Context
import com.example.composerecipeapp.api.RecipeApi
import com.example.composerecipeapp.helper.mapper.RecipeDtoMapper
import com.example.composerecipeapp.viewmodel.RecipeRepository
import com.example.composerecipeapp.viewmodel.RecipeRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Singleton
    @Provides
    fun provideRecipeDtoMapper(): RecipeDtoMapper = RecipeDtoMapper()

    @Singleton
    @Provides
    fun provideRecipeApi(): RecipeApi = Retrofit.Builder()
        .baseUrl(RecipeApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(RecipeApi::class.java)

    @Singleton
    @Provides
    fun provideRecipeRepository(recipeApi: RecipeApi, recipeDtoMapper: RecipeDtoMapper): RecipeRepository =
        RecipeRepositoryImpl(recipeApi, recipeDtoMapper)
}