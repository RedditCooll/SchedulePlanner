package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.model.ChatRoom
import com.redditcooll.schedulePlanner.repo.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatRoomService {
    @Autowired
    private lateinit var chatRoomRepository: ChatRoomRepository

    fun getChatId(senderId: String?, recipientId: String?, createIfNotExist: Boolean): String {
        var result = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
        return if(result == null){
            if (!createIfNotExist) {
                ""
            } else{
                var chatId = String.format("%s_%s", senderId, recipientId)
                var senderRecipient = ChatRoom(chatId= chatId, senderId = senderId, recipientId = recipientId)
                var recipientSender = ChatRoom(chatId= chatId, senderId = senderId, recipientId = recipientId)
                chatRoomRepository.save(senderRecipient)
                chatRoomRepository.save(recipientSender)
                chatId
            }
        } else
            result.chatId!!
    }
}