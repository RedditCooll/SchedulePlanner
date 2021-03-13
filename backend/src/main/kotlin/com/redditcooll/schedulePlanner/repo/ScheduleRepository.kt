package com.redditcooll.schedulePlanner.repo

import com.redditcooll.schedulePlanner.model.ScheduleEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface ScheduleRepository: CrudRepository<ScheduleEntity?, Long?> {
    @Query("SELECT * FROM SCHEDULE WHERE SCHEDULE_ID = :id", nativeQuery = true)
    fun findById(id:String): ScheduleEntity?
}
