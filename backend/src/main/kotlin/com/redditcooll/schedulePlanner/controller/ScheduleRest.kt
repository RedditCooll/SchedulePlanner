package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.service.ExcelService
import com.redditcooll.schedulePlanner.service.ScheduleService
import com.redditcooll.schedulePlanner.to.ScheduleTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/api")
class ScheduleRest {

    @Autowired
    private lateinit var scheduleService: ScheduleService

    @Autowired
    private lateinit var excelService: ExcelService

    @RequestMapping(value= ["/create/schedules"], method = [RequestMethod.POST], produces = ["application/json"])
    fun createBusinessContent(@RequestBody scheduleToList: MutableList<ScheduleTo>, request: HttpServletRequest): Boolean {
        return scheduleService.createSchedules(scheduleToList)
    }

    @RequestMapping(value= ["/get/schedules"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getBusinessContent(request: HttpServletRequest): MutableIterable<ScheduleTo?> {
        return scheduleService.getSchedules()
    }

    @RequestMapping(value = ["/schedules/upload"], method = [RequestMethod.POST])
    fun importExcel(@RequestParam file: MultipartFile): MutableIterable<ScheduleTo?> {
        return excelService.importExcel(file)
    }

    @RequestMapping(value = ["/schedules/download"], method = [RequestMethod.POST])
    fun exportExcel(@RequestBody scheduleToList: MutableList<ScheduleTo>, request: HttpServletRequest): ResponseEntity<InputStreamResource> {
        var fileName = "schedulePlan_${Date()}.xlsx"
        var excelByteArrayInputStream = excelService.exportExcel(scheduleToList)
        val respHeaders = HttpHeaders()
        respHeaders.contentType = MediaType("application", "octet-stream")
        respHeaders.cacheControl = "must-revalidate, post-check=0, pre-check=0"
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$fileName")
        return ResponseEntity.ok().headers(respHeaders).body((InputStreamResource(excelByteArrayInputStream!!)))
    }
}