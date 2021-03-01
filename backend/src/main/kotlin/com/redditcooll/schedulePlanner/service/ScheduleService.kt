package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.model.ScheduleEntity
import com.redditcooll.schedulePlanner.repo.ScheduleRepository
import com.redditcooll.schedulePlanner.to.Rate
import com.redditcooll.schedulePlanner.to.ScheduleTo
import com.redditcooll.schedulePlanner.to.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.sql.Date

@Service
class ScheduleService {

    var logger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(ScheduleService::class.java)

    @Autowired
    private lateinit var scheduleRepository: ScheduleRepository

    fun createSchedules(scheduleList : MutableList<ScheduleTo>): Boolean{
        logger.info("createSchedules count: ${scheduleList.size}")
        return try {
            var scheduleEntityList = mutableListOf<ScheduleEntity>()
            scheduleList.forEach{
                var scheduleEntity = ScheduleEntity()
                scheduleEntity.scheduleId = it.id
                scheduleEntity.date = it.date
                scheduleEntity.userId = it.user!!.id
                scheduleEntity.priority = it.priority
                scheduleEntity.status = it.status
                scheduleEntity.classification = it.classification
                scheduleEntity.content = it.content
                scheduleEntity.address = it.address
                scheduleEntity.veryGood = 0
                scheduleEntity.good = 0
                scheduleEntity.like = 0
                scheduleEntityList.add(scheduleEntity)
                //scheduleRepository.save(scheduleEntity)
            }
            scheduleRepository.saveAll(scheduleEntityList)
            true
        }
        catch (e: IOException){
            false
        }
        return false
    }

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

            var userTo = User()
            userTo.id = it.userId
            scheduleTo.user = userTo

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