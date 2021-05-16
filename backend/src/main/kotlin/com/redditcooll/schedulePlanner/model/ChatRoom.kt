package com.redditcooll.schedulePlanner.model

import org.springframework.data.annotation.Id

class ChatRoom constructor(chatId: String? = null, senderId: String? = null, recipientId: String? = null) {
    @Id
    var id: String? = null
    var chatId: String? = chatId
    var senderId: String? = senderId
    var recipientId: String? = recipientId
}