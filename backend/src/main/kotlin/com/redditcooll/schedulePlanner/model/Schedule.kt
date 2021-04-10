package com.redditcooll.schedulePlanner.model


import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "SCHEDULE")
class Schedule {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "SCHEDULE_ID")
    var scheduleId: String? = null

    @Column(name = "_DATE")
    var date: LocalDate? = null

    @Column(name = "USER_ID")
    var userId: String? = null

    @Column(name = "PRIORITY")
    var priority: Int? = null

    @Column(name = "STATUS")
    var status: String? = null

    @Column(name = "CLASSIFICATION")
    var classification: String? = null

    @Column(name = "CONTENT")
    var content: String? = null

    @Column(name = "ADDRESS")
    var address: String? = null

    @Column(name = "VERY_GOOD")
    var veryGood: Int? = null

    @Column(name = "GOOD")
    var good: Int? = null

    @Column(name = "_LIKE")
    var like: Int? = null
}