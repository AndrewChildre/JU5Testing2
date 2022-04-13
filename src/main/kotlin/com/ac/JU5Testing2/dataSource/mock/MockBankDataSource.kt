package com.ac.JU5Testing2.dataSource.mock

import com.ac.JU5Testing2.dataSource.BankDataSource
import com.ac.JU5Testing2.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("1234", 0.2, 3),
        Bank("4321", 1.2, 6),
        Bank("5150", 3.2, 1),
    )

    override fun retreiveBanks(): Collection<Bank> = banks

    override fun retreiveBank(accountNumber: String): Bank = banks.firstOrNull() { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("Could not find a bank with that account number: $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber })
            throw java.lang.IllegalArgumentException("Account number ${bank.accountNumber} already exists")
        banks.add(bank)
        return bank
    }

    override fun patchBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Account number ${bank.accountNumber} already exists")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Account number ${accountNumber} already exists")
        banks.remove(currentBank)
    }


}
