package com.redditcooll.schedulePlanner.repo

import com.redditcooll.schedulePlanner.model.Schedule
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository: CrudRepository<Schedule?, Long?> {
    @Query("SELECT * FROM schedulePlanner.SCHEDULE WHERE SCHEDULE_ID = :id", nativeQuery = true)
    fun findById(id:String): Schedule?
}
