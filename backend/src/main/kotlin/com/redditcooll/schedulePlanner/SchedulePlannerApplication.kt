package com.redditcooll.schedulePlanner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
class SchedulePlannerApplication: SpringBootServletInitializer() {
	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(SchedulePlannerApplication::class.java)
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			val app = SpringApplicationBuilder(SchedulePlannerApplication::class.java)
			app.run()
		}
	}
}

// FIXME: showing login as .
// TODO: arrange config
// TODO: add table columns for content, pic
// col rename 'content' to 'subject'
// add content col

