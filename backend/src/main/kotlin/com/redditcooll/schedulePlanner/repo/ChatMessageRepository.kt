package com.redditcooll.schedulePlanner.repo

import com.redditcooll.schedulePlanner.model.ChatMessage
import com.redditcooll.schedulePlanner.model.MessageStatus
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageRepository : MongoRepository<ChatMessage?, String?> {
    fun countBySenderIdAndRecipientIdAndStatus(senderId: String?, recipientId: String?, status: MessageStatus?): Long

    fun findByChatId(chatId: String?): MutableList<ChatMessage>?
}