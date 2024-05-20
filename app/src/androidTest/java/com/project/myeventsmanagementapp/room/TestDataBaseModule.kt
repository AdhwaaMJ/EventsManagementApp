package com.project.myeventsmanagementapp.room

import android.content.Context
import androidx.room.Room
import com.project.myeventsmanagementapp.data.database.EventsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDataBaseModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,EventsDatabase::class.java
        ).allowMainThreadQueries()
            .build()
}