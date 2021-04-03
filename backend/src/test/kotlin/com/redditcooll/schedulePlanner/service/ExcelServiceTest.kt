package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.service.ExcelService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExcelServiceTest {

    @Autowired
    private lateinit var excelService: ExcelService

    @Test
    fun loadExcelTest(){
        excelService.loadExcel()
    }
}