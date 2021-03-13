package com.redditcooll.schedulePlanner

import com.redditcooll.schedulePlanner.repo.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


@SpringBootApplication
class SchedulePlannerApplication

fun main(args: Array<String>) {
	runApplication<SchedulePlannerApplication>(*args)
}


// TODO: add table columns for content, pic
// col rename 'content' to 'subject'
// add content col

