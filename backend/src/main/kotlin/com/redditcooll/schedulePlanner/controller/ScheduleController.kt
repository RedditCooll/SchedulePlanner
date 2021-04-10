package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.config.CurrentUser
import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.service.ScheduleService
import com.redditcooll.schedulePlanner.service.ExcelService
import com.redditcooll.schedulePlanner.dto.ScheduleTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/api")
class ScheduleController {

    @Autowired
    private lateinit var scheduleService: ScheduleService

    @Autowired
    private lateinit var excelService: ExcelService

    @RequestMapping(value= ["/create/schedules"], method = [RequestMethod.POST], produces = ["application/json"])
    fun createBusinessContent(@RequestBody scheduleToList: MutableList<ScheduleTo>, @CurrentUser user: LocalUser?): Boolean {
        return scheduleService.createSchedules(scheduleToList, user)
    }

    @RequestMapping(value= ["/get/schedules"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getBusinessContents(request: HttpServletRequest): MutableIterable<ScheduleTo?> {
        return scheduleService.getSchedules()
    }

    @RequestMapping(value= ["/get/schedule"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getBusinessContent(@RequestParam id:String, request: HttpServletRequest): ScheduleTo? {
        return scheduleService.getSchedule(id)
    }

    @RequestMapping(value = ["/schedules/upload"], method = [RequestMethod.POST])
    fun importExcel(@RequestParam file: MultipartFile, @CurrentUser user: LocalUser?): MutableIterable<ScheduleTo?> {
        return excelService.importExcel(file, user)
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