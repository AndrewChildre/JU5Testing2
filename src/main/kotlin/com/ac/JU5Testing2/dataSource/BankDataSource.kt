package com.ac.JU5Testing2.dataSource

import com.ac.JU5Testing2.model.Bank


interface BankDataSource {
    fun retreiveBanks(): Collection<Bank>
    fun retreiveBank(accountNumber: String): Bank
    fun createBank(bank: Bank): Bank
    fun patchBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String)
}