package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.model.ScheduleEntity
import com.redditcooll.schedulePlanner.service.ScheduleService
import com.redditcooll.schedulePlanner.to.ScheduleTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class ScheduleRest {

    @Autowired
    private lateinit var scheduleService: ScheduleService

    // TODO: receive excel upload, return PlanTo

    // TODO: receive PlanTo, export it to excel

    // TODO: save Schedule

    // TODO: get all Schedule List
    @RequestMapping(value= ["/schedules"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getBusinessContent(request: HttpServletRequest): MutableIterable<ScheduleTo?> {
        return scheduleService.getSchedules()
    }
}