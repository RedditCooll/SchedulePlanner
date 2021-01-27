package com.redditcooll.schedulePlanner.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExcelUtilServiceTest {

    @Autowired
    private lateinit var excelUtilService: ExcelUtilService

    @Test
    fun loadExcelTest(){
        excelUtilService.loadExcel()
    }

}