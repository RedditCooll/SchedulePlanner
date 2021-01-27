package com.redditcooll.schedulePlanner.service

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.IOException
import kotlin.jvm.Throws


@Service
class ExcelUtilService {

    @Value("classpath:static/weekly-schedule-monday-to-friday-with-room-for-notes-in-color.xlsx")
    private val resource: Resource? = null

    // TODO: add load excel file service
    @Throws(IOException::class)
    fun loadExcel() {
        val excelFile = FileInputStream(resource!!.file)
        val workbook = XSSFWorkbook(excelFile)

        val sheet = workbook.getSheet("Weekly schedule")
        val rows = sheet.iterator()
        while (rows.hasNext()) {
            val currentRow = rows.next()
            val cellsInRow = currentRow.iterator()
            while (cellsInRow.hasNext()) {
                val currentCell = cellsInRow.next()
                if (currentCell.getCellTypeEnum() === CellType.STRING) {
                    print(currentCell.getStringCellValue() + " | ")
                } else if (currentCell.getCellTypeEnum() === CellType.NUMERIC) {
                    print(currentCell.getNumericCellValue().toString() + "(numeric)")
                }
            }

            println()
        }

        workbook.close()
        excelFile.close()
    }

    // TODO: add export excel file service
    fun exportExcel() {

    }
}