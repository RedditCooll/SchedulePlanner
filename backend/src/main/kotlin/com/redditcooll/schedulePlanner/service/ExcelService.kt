package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.dto.Rate
import com.redditcooll.schedulePlanner.dto.ScheduleTo
import com.redditcooll.schedulePlanner.model.User
import com.redditcooll.schedulePlanner.util.PoiUtilService
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.time.LocalDate
import kotlin.jvm.Throws


@Service
class ExcelService {

    var logger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(ExcelService::class.java)

    @Value("classpath:static/weekly-schedule-monday-to-friday-with-room-for-notes-in-color.xlsx")
    private val resource: Resource? = null

    @Autowired
    private lateinit var poiUtilService: PoiUtilService

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
                if (currentCell.cellTypeEnum === CellType.STRING) {
                    print(currentCell.stringCellValue + " | ")
                } else if (currentCell.cellTypeEnum === CellType.NUMERIC) {
                    print(currentCell.numericCellValue.toString() + "(numeric)")
                }
            }
            println()
        }
        workbook.close()
        excelFile.close()
    }

    fun importExcel(file: MultipartFile, currentUser: LocalUser?): MutableIterable<ScheduleTo?>{
        logger.info("Start importExcel")
        // TODO: add check mechanism
        var excelDataList = poiUtilService.readExcel(file)
        var scheduleToList = mutableListOf<ScheduleTo>()
        try {
            if (excelDataList != null) {
                for (row in excelDataList){
                    var scheduleTo = ScheduleTo()
                    var dataArr = row[0]!!.split("-")
                    scheduleTo.date = LocalDate.of(dataArr[0].toInt(), dataArr[1].toInt(), dataArr[2].toInt())
                    scheduleTo.content = row[1]
                    scheduleTo.priority = row[2]!!.toInt()
                    scheduleTo.classification = row[3]
                    scheduleTo.address = row[4]
                    scheduleTo.status = row[5]

                    var user = User()
                    user.id = currentUser!!.user.id
                    user.displayName = currentUser!!.user.displayName
                    scheduleTo.user = user

                    var rateTo = Rate()
                    scheduleTo.rate = rateTo

                    scheduleToList.add(scheduleTo)
                }
            }
            else
                logger.info("Excel is empty!")
        }
        catch (e: Exception){
            logger.error("Error when parsing excel content to ScheduleTo!" ,e)
        }

        logger.info("Finish importExcel")
        return scheduleToList
    }

    @Throws(IOException::class)
    fun exportExcel(scheduleToList: MutableList<ScheduleTo>): ByteArrayInputStream? {
        logger.info("Start exportExcel")
        val columns = arrayOf("Date", "Content", "Priority", "Classification", "Address", "Status")
        XSSFWorkbook().use { workbook ->
            ByteArrayOutputStream().use { out ->
                val createHelper: CreationHelper = workbook.creationHelper
                val sheet: Sheet = workbook.createSheet("Schedule")
                val headerFont: Font = workbook.createFont()
                headerFont.bold = true
                headerFont.color = IndexedColors.BLUE.getIndex()
                val headerCellStyle: CellStyle = workbook.createCellStyle()
                headerCellStyle.setFont(headerFont)

                // Row for Header
                val headerRow: Row = sheet.createRow(0)

                // Header
                for (col in columns.indices) {
                    val cell: Cell = headerRow.createCell(col)
                    cell.setCellValue(columns[col])
                    cell.cellStyle = headerCellStyle
                }

                var rowIdx = 1
                for (customer in scheduleToList) {
                    val row: Row = sheet.createRow(rowIdx++)
                    row.createCell(0).setCellValue(customer.date!!.toString())
                    row.createCell(1).setCellValue(customer.content)
                    row.createCell(2).setCellValue(customer.priority.toString())
                    row.createCell(3).setCellValue(customer.classification)
                    row.createCell(4).setCellValue(customer.address)
                    row.createCell(5).setCellValue(customer.status)
                }
                workbook.write(out)
                return ByteArrayInputStream(out.toByteArray())
            }
        }
        logger.info("Finish exportExcel")
    }
}