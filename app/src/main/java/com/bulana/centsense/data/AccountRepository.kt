package com.bulana.centsense.data

import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun insertAccount(account: Account)

    suspend fun getAccountById(id: Int): Account

    fun getAllAccounts(): Flow<List<Account>>

    suspend fun deleteAccount(account: Account)

}