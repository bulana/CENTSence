package com.bulana.centsense.di

import android.app.Application
import androidx.room.Room
import com.bulana.centsense.data.AccountDatabase
import com.bulana.centsense.data.AccountRepository
import com.bulana.centsense.data.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAccountDatabase(app: Application): AccountDatabase {
        return Room.databaseBuilder(
            app,
            AccountDatabase::class.java,
            "account_db"
        ).build()
    }

    @Provides
    fun provideAccountRepository(db: AccountDatabase): AccountRepository {
        return AccountRepositoryImpl(db.dao)
    }

}