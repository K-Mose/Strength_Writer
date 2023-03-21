package com.example.strengthwriter.di

import android.content.Context
import androidx.room.Room
import com.example.strengthwriter.data.WriterDatabase
import com.example.strengthwriter.utils.Constants.STRENGTH_WRITER_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WriterDatabase =
        Room.databaseBuilder(
            context,
            WriterDatabase::class.java,
            STRENGTH_WRITER_DATABASE
        ).build()

    @Provides
    @Singleton
    fun provideDailyMissionDao(database: WriterDatabase) = database.dailyMissionDao()

    @Provides
    @Singleton
    fun provideWorkoutDao(database: WriterDatabase) = database.workoutDao()

    @Provides
    @Singleton
    fun provideSetsDao(database: WriterDatabase) = database.setsDao()
}