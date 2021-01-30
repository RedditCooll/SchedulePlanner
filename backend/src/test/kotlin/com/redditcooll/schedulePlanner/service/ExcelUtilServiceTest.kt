package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.repo.ScheduleRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
class ExcelUtilServiceTest {

    @Autowired
    private lateinit var excelUtilService: ExcelUtilService

    @Test
    fun loadExcelTest(){
        excelUtilService.loadExcel()
    }
}