package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.model.ChatMessage
import com.redditcooll.schedulePlanner.model.ChatNotification
import com.redditcooll.schedulePlanner.service.ChatMessageService
import com.redditcooll.schedulePlanner.service.ChatRoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ChatController {
    @Autowired
    private val messagingTemplate: SimpMessagingTemplate? = null

    @Autowired
    private val chatMessageService: ChatMessageService? = null

    @Autowired
    private val chatRoomService: ChatRoomService? = null

    @MessageMapping("/chat")
    fun processMessage(@Payload chatMessage: ChatMessage) {
        val chatId = chatRoomService!!.getChatId(chatMessage.senderId, chatMessage.recipientId, true)
        chatMessage.chatId = chatId
        val saved = chatMessageService!!.save(chatMessage)
        messagingTemplate!!.convertAndSendToUser(
            chatMessage.recipientId!!, "/queue/messages",
            ChatNotification(saved.id!!, saved.senderId!!, saved.senderName!!)
        )
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    fun countNewMessages(
        @PathVariable senderId: String?,
        @PathVariable recipientId: String?
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(chatMessageService!!.countNewMessages(senderId, recipientId))
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    fun findChatMessages(
        @PathVariable senderId: String?,
        @PathVariable recipientId: String?
    ): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService!!.findChatMessages(senderId, recipientId))
    }

    @GetMapping("/messages/{id}")
    fun findMessage(@PathVariable id: String?): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService!!.findById(id!!))
    }
}