package com.ac.JU5Testing2.service

import com.ac.JU5Testing2.dataSource.BankDataSource
import com.ac.JU5Testing2.dataSource.mock.MockBankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk(relaxed =  true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source`(){
        //given

        //when
            val banks = bankService.getBanks()
        //then
        verify(exactly = 1) { dataSource.retreiveBanks() }
    }
}