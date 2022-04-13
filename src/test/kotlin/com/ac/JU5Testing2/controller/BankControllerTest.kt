package com.ac.JU5Testing2.controller

import com.ac.JU5Testing2.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(var mockMvc: MockMvc, var objectMapper: ObjectMapper) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("getBanks")
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // Given
            mockMvc.get(baseUrl)
                // When
                .andDo { print() }
                //Then
                .andExpect {
                    status { isOk() }
                    content { content { MediaType.APPLICATION_JSON } }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }

    @Nested
    @DisplayName("getBank")
    inner class GetBank {
        @Test
        fun `should return specific bank`() {
            val accountNumber = 1234
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("0.2") }
                    jsonPath("$.transactionFee") { value("3") }

                }
        }

        @Test
        fun `should return NOT FOUND for no account number`() {
            val accountNumber = "does_not_exist"
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST newBank")
    inner class PostBank {
        @Test
        fun `should post new bank`() {
            // Given
            val newBank = Bank("new1234", 3.14, 5)
            //When
            val perfornPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            //Then
            perfornPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }
    }

    @Test
    fun `should return BAD REQUEST if account number already exists`() {
        val invalidBank = Bank("1234", 3.14, 33)
        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)

        }
        performPost
            .andDo { print() }
            .andExpect { status { isBadRequest() } }

    }

    @Nested
    @DisplayName("PATCH api/banks")
    inner class PatchABank {
        @Test
        fun `should update an existing bank`() {
            // Given
            val updatedBank = Bank("1234", 3.4, 9)

            // When
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            // Then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }

        @Test
        fun `should return BAD REQUEST if no account number exists`() {
            // Given
            val invalidBank = Bank("does_NOT_exist", 1.0, 1)
            // When
            val perfornPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            //Then
            perfornPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE aip/bank")
    inner class DeleteBank {
        @Test
        fun `should delete bank with the given acct number`() {
            val accountNumber = 1234
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }
            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }
        @Test
        fun`should return NOT FOUND if the acct number doesn't exist`(){
            val invalidAccountNumber = "does_NOT_exist"
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

    }

}