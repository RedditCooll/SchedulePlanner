package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.exception.ResourceNotFoundException
import com.redditcooll.schedulePlanner.model.ChatMessage
import com.redditcooll.schedulePlanner.model.MessageStatus
import com.redditcooll.schedulePlanner.repo.ChatMessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class ChatMessageService {
    @Autowired
    private lateinit var repository: ChatMessageRepository

    @Autowired
    private val chatRoomService: ChatRoomService? = null

    @Autowired
    private lateinit var mongoOperations: MongoOperations

    fun save(chatMessage: ChatMessage): ChatMessage {
        chatMessage.status = MessageStatus.RECEIVED
        repository.save(chatMessage)
        return chatMessage
    }

    fun countNewMessages(senderId: String?, recipientId: String?): Long {
        return repository.countBySenderIdAndRecipientIdAndStatus(
            senderId, recipientId, MessageStatus.RECEIVED
        )
    }

    fun findChatMessages(senderId: String?, recipientId: String?): List<ChatMessage> {
        val chatId = chatRoomService!!.getChatId(senderId, recipientId, false)
        var messages = mutableListOf<ChatMessage>()
        if(!chatId.isNullOrEmpty()){
            messages = repository.findByChatId(chatId)!!
        }
        if (messages.size > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED)
        }
        return messages
    }

    fun findById(id: String): ChatMessage {
        return repository
            .findById(id)
            .map { chatMessage ->
                chatMessage!!.status = MessageStatus.DELIVERED
                repository.save(chatMessage)
            }
            .orElseThrow { ResourceNotFoundException("Message", "id" , id) }
    }

    fun updateStatuses(senderId: String?, recipientId: String?, status: MessageStatus?) {
        val query = Query(
            Criteria
                .where("senderId").`is`(senderId)
                .and("recipientId").`is`(recipientId)
        )
        val update = Update.update("status", status)
        mongoOperations.updateMulti(query, update, ChatMessage::class.java)
    }
}