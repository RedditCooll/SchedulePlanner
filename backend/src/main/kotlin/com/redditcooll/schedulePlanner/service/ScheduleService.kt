package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.model.ScheduleEntity
import com.redditcooll.schedulePlanner.repo.ScheduleRepository
import com.redditcooll.schedulePlanner.to.Rate
import com.redditcooll.schedulePlanner.to.ScheduleTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScheduleService {

    @Autowired
    private lateinit var scheduleRepository: ScheduleRepository

    // TODO: save Schedule into DB
    fun saveSchedule(scheduleList : List<ScheduleTo>){

    }

    // TODO: get Schedule from DB
    fun getSchedules(): MutableIterable<ScheduleTo?> {
        var result = scheduleRepository.findAll()
        var scheduleToList = mutableListOf<ScheduleTo>()
        result.forEach{
            var scheduleTo = ScheduleTo()
            scheduleTo.id = it!!.scheduleId
            scheduleTo.address = it.address
            scheduleTo.classification = it.classification
            scheduleTo.content = it.content
            scheduleTo.priority = it.priority
            scheduleTo.status = it.status
            scheduleTo.date = it.date

            var rateTo = Rate()
            rateTo.good = it.good
            rateTo.veryGood = it.veryGood
            rateTo.like = it.like
            scheduleTo.rate = rateTo

            scheduleToList.add(scheduleTo)
        }
        return scheduleToList
    }
}