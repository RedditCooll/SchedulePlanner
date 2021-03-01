package com.redditcooll.schedulePlanner.util

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

@Service
class PoiUtilService {
    var logger: org.slf4j.Logger = LoggerFactory.getLogger(PoiUtilService::class.java)
    private val xls = "xls"
    private val xlsx = "xlsx"

    @Throws(IOException::class)
    fun readExcel(file: MultipartFile): List<Array<String?>>? {
        checkFile(file)
        val workbook = getWorkBook(file)
        val list: MutableList<Array<String?>> = ArrayList()
        if (workbook != null) {
            for (sheetNum in 0 until workbook.numberOfSheets) {
                val sheet = workbook.getSheetAt(sheetNum) ?: continue
                val firstRowNum = sheet.firstRowNum
                val lastRowNum = sheet.lastRowNum
                for (rowNum in firstRowNum + 1..lastRowNum) {
                    val row = sheet.getRow(rowNum) ?: continue
                    val firstCellNum: Short = row.firstCellNum
                    val lastCellNum = row.physicalNumberOfCells
                    val cells = arrayOfNulls<String>(row.physicalNumberOfCells)
                    for (cellNum in firstCellNum until lastCellNum) {
                        val cell = row.getCell(cellNum)
                        cells[cellNum] = getCellValue(cell)
                    }
                    list.add(cells)
                }
            }
            workbook.close()
        }
        return list
    }

    @Throws(IOException::class)
    fun checkFile(file: MultipartFile?) {
        if (null == file) {
            logger.error("File does not exist！")
            throw FileNotFoundException("File does not exist！")
        }
        val fileName = file.originalFilename
        if (!fileName!!.endsWith(xls) && !fileName.endsWith(xlsx)) {
            logger.error(fileName + "is not excel file")
            throw IOException(fileName + "is not excel file")
        }
    }

    fun getWorkBook(file: MultipartFile): Workbook? {
        val fileName = file.originalFilename
        var workbook: Workbook? = null
        try {
            val `is` = file.inputStream
            if (fileName!!.endsWith(xls)) {
                //2003
                workbook = HSSFWorkbook(`is`)
            } else if (fileName.endsWith(xlsx)) {
                //2007
                workbook = XSSFWorkbook(`is`)
            }
        } catch (e: IOException) {
            logger.info(e.message)
        }
        return workbook
    }

    fun getCellValue(cell: Cell?): String? {
        var cellValue = ""
        if (cell == null) {
            return cellValue
        }
        if (cell.cellType == Cell.CELL_TYPE_NUMERIC) {
            cell.cellType = Cell.CELL_TYPE_STRING
        }
        cellValue = when (cell.cellType) {
            Cell.CELL_TYPE_NUMERIC -> cell.numericCellValue.toString()
            Cell.CELL_TYPE_STRING -> cell.stringCellValue.toString()
            Cell.CELL_TYPE_BOOLEAN -> cell.booleanCellValue.toString()
            Cell.CELL_TYPE_FORMULA -> cell.cellFormula.toString()
            Cell.CELL_TYPE_BLANK -> ""
            Cell.CELL_TYPE_ERROR -> "illegal"
            else -> "unknown"
        }
        return cellValue
    }
}