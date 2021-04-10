package com.redditcooll.schedulePlanner.dto

import com.redditcooll.schedulePlanner.model.User
import java.time.LocalDate

class ScheduleTo {
    var id: String? = null
    var date: LocalDate? = null
    var user: User? = null
    var priority: Int? = null
    var status: String? = null
    var classification: String? = null
    var content: String? = null
    var address: String? = null
    var rate: Rate? = null
}

class Rate {
    var veryGood: Int? = null
    var good: Int? = null
    var like: Int? = null
}

class ScheduleTableTo {
    var period: String? = null
    var monday: ScheduleTo? = null
    var tuesday: ScheduleTo? = null
    var wednesday: ScheduleTo? = null
    var thursday: ScheduleTo? = null
    var friday: ScheduleTo? = null
    var saturday: ScheduleTo? = null
    var sunday: ScheduleTo? = null
}