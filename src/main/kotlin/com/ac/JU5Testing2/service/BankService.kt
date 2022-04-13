package com.ac.JU5Testing2.service

import com.ac.JU5Testing2.dataSource.BankDataSource
import com.ac.JU5Testing2.model.Bank
import org.springframework.stereotype.Service


@Service
class BankService(private val dataSource: BankDataSource){
    fun getBanks(): Collection<Bank> = dataSource.retreiveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retreiveBank(accountNumber)
    fun newBank(bank: Bank): Bank = dataSource.createBank(bank)
    fun updateBank(bank: Bank): Bank  = dataSource.patchBank(bank)
    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)


}

