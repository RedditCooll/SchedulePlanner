package com.redditcooll.schedulePlanner.model


import java.sql.Date
import javax.persistence.*


@Entity
@Table(name = "SCHEDULE")
class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    val scheduleId: String? = null

    @Column(name = "_DATE")
    val date: Date? = null

    @Column(name = "USER_ID")
    val userId: String? = null

    @Column(name = "PRIORITY")
    val priority: Int? = null

    @Column(name = "STATUS")
    val status: String? = null

    @Column(name = "CLASSIFICATION")
    val classification: String? = null

    @Column(name = "CONTENT")
    val content: String? = null

    @Column(name = "ADDRESS")
    val address: String? = null

    @Column(name = "VERY_GOOD")
    val veryGood: Int? = null

    @Column(name = "GOOD")
    val good: Int? = null

    @Column(name = "_LIKE")
    val like: Int? = null
}