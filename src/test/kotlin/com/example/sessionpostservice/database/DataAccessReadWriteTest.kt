package com.example.sessionpostservice.database

import com.example.sessionpostservice.database.document.TestEntity
import com.example.sessionpostservice.database.repository.DataAccessReadWriteRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class DataAccessReadWriteSpec: BehaviorSpec(){

    @Autowired
    lateinit var dataAccessReadWriteRepository: DataAccessReadWriteRepository
    init {
        given("a database") {
            dataAccessReadWriteRepository.save(
                TestEntity(
                    name = "test"
                )
            )
            `when`("a write operation is performed") {
                val read = dataAccessReadWriteRepository.findAll()
                then("the read operation should return the written data") {
                    read.size shouldBe 1
                    read[0].name shouldBe "test"
                }
            }
        }
    }
}

@DataJpaTest
class DataAccessReadWriteSpec2 {
    @Autowired
    var employeeRepository: DataAccessReadWriteRepository? = null

    @Test
    fun employeeTest(){
        employeeRepository?.save(
            TestEntity(
                name = "choi"
            )
        )

        val result = employeeRepository?.findAll()
        assertThat(result?.size).isEqualTo(1)
        assertThat(result?.get(0)?.name).isEqualTo("choi")
    }
}

