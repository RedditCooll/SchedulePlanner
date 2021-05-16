package com.redditcooll.schedulePlanner.repo

import com.redditcooll.schedulePlanner.model.ChatRoom
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRoomRepository : MongoRepository<ChatRoom?, String?> {
    fun findBySenderIdAndRecipientId(senderId: String?, recipientId: String?): ChatRoom?
}