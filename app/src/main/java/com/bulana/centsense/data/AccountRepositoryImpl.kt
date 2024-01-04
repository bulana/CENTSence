package com.bulana.centsense.data

import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val dao: AccountDao
) : AccountRepository {

    override suspend fun insertAccount(
        account: Account
    ) {
        dao.insertAccount(account)
    }

    override suspend fun getAccountById(
        id: Int
    ): Account = dao.getAccountById(id)


    override fun getAllAccounts(
    ): Flow<List<Account>> = dao.getAllAccounts()


    override suspend fun deleteAccount(id: Account) {
        dao.deleteAccount(id)
    }

}