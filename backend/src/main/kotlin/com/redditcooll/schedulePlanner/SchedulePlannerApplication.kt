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

/** back-end **/
// TODO: Add data into H2
// TODO: col rename 'content' to 'subject', add content in ScheduleTo
// TODO: add table columns for profile picture
// TODO: revise config files
// TODO: Unify date format in both front-end & back-end

/** front-end **/
// TODO: add logout button
// TODO: add validator for register
// FIXME: when redirect to scheduleTextEditorPage, 'Cannot read property 'id' of undefined'
