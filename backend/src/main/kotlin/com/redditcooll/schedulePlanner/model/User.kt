package com.redditcooll.schedulePlanner.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USER")
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    var id: Long? = null

    @Column(name = "PROVIDER_USER_ID")
    var providerUserId: String? = null
    var email: String? = null

    @Column(name = "ENABLED", columnDefinition = "BIT", length = 1)
    var enabled = false

    @Column(name = "DISPLAY_NAME")
    var displayName: String? = null

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null

    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    var modifiedDate: Date? = null

    @Column(name = "PASSWORD")
    var password: String? = null

    @Column(name = "PROVIDER")
    var provider: String? = null

    // bi-directional many-to-many association to Role
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns = [JoinColumn(name = "USER_ID")], inverseJoinColumns = [JoinColumn(name = "ROLE_ID")])
    var roles: Set<Role>? = null

    companion object {
        private var serialVersionUID = 65981149772133526L
    }
}