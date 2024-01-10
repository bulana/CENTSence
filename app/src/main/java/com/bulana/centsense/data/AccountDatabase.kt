package com.bulana.centsense.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bulana.centsense.util.DateConverter

@Database(
    entities = [Account::class],
    version = 3
)
@TypeConverters(DateConverter::class)
abstract class AccountDatabase : RoomDatabase() {
    abstract val dao: AccountDao
}