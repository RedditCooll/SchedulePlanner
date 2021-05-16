package com.redditcooll.schedulePlanner.model

import org.springframework.data.annotation.Id
import java.util.*

class ChatMessage {
    @Id
    var id: String? = null
    var chatId: String? = null
    var senderId: String? = null
    var recipientId: String? = null
    var senderName: String? = null
    var recipientName: String? = null
    var content: String? = null
    var timestamp: Date? = null
    var status: MessageStatus? = null
}