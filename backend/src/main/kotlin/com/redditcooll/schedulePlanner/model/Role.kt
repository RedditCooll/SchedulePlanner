package com.redditcooll.schedulePlanner.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ROLE")
class Role(@Column(name = "NAME") var name: String?) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    var roleId: Long? = null

    // bi-directional many-to-many association to User
    @ManyToMany(mappedBy = "roles")
    var users: Set<User>? = null
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val role = obj as Role
        return role.equals(role.name)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Role [name=").append(name).append("]").append("[id=").append(roleId).append("]")
        return builder.toString()
    }

    companion object {
        private const val serialVersionUID = 1L
        const val USER = "USER"
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_ADMIN = "ROLE_ADMIN"
        const val ROLE_MODERATOR = "ROLE_MODERATOR"
    }

}