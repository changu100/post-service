package com.example.sessionpostservice.database.repository

import com.example.sessionpostservice.database.document.TestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataAccessReadWriteRepository : JpaRepository<TestEntity, Long>
