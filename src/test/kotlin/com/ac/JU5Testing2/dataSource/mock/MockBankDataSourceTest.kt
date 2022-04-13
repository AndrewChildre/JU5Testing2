package com.ac.JU5Testing2.dataSource.mock

import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should return a list of banks`() {
        //when
        val banks = mockDataSource.retreiveBanks()
        //then
        assert(banks.size >= 3)
    }
    @Test
    fun `should return some mock data`() {
        val banks = mockDataSource.retreiveBanks()

        assert(banks.any{it.accountNumber.isNotBlank()})
        assert(banks.all {it.trust != 0.0 })
        assert(banks.any{it.transactionFee >= 1})
    }
}