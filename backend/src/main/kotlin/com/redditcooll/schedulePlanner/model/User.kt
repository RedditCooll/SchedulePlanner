package com.redditcooll.schedulePlanner.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
@Getter
@Setter
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    var id: Long? = null

    @Column(name = "PROVIDER_USER_ID")
    var providerUserId: String? = null
    var email: String? = null

    @Column(name = "enabled", columnDefinition = "BIT", length = 1)
    var enabled = false

    @Column(name = "DISPLAY_NAME")
    var displayName: String? = null

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    var modifiedDate: Date? = null
    var password: String? = null
    var provider: String? = null

    // bi-directional many-to-many association to Role
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "USER_ID")], inverseJoinColumns = [JoinColumn(name = "ROLE_ID")])
    var roles: Set<Role>? = null

    companion object {
        private var serialVersionUID = 65981149772133526L
    }
}