package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.service.ScheduleService
import com.redditcooll.schedulePlanner.to.ScheduleTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/api")
class ScheduleRest {

    @Autowired
    private lateinit var scheduleService: ScheduleService

    // TODO: receive excel upload, return PlanTo

    // TODO: receive PlanTo, export it to excel

    @RequestMapping(value= ["/create/schedules"], method = [RequestMethod.POST], produces = ["application/json"])
    fun createBusinessContent(@RequestBody scheduleToList: MutableList<ScheduleTo>, request: HttpServletRequest): Boolean {
        return scheduleService.createSchedules(scheduleToList)
    }

    @RequestMapping(value= ["/get/schedules"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getBusinessContent(request: HttpServletRequest): MutableIterable<ScheduleTo?> {
        return scheduleService.getSchedules()
    }
}