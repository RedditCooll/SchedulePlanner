import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../Schedule/schedule.model';
import { MessageService } from '../message.service'
import { Channel, Message } from './chat.model';
// import { Stomp } from '@stomp/stompjs';
// import * as SockJS from 'sockjs-client';
declare var SockJS;
declare var Stomp;

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.less']
})
export class ChatComponent implements OnInit {

  constructor(
    private messageService: MessageService,
    ) {}

  public CHAT_SERVICE = "http://localhost:8080";
  public stompClient;

  channelList: Channel[] = []; // all the channel data
  messages: Message[] = [];    // current showing messages 
  newMessage = "";             // input new message
  currentUser: User;           // who signed in
  currentReciptient: User;     // channel user which is selected
  
  
  ngOnInit(): void {
    // Get currentUser
    this.currentUser = this.messageService.currentUser;
    
    // Get all users and messages
    this.loadContacts();
    this.initializeWebSocketConnection();
  }

  ngOnChanges(){

  }

  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe( "/user/" + that.currentUser.id + "/queue/messages",
      that.onMessageReceived);
    }, this.onError);
  }

  onError = (err) => {
    console.log('error!',err);
  };

  onMessageReceived = (msg) => {
    console.log('onMessageReceived!', msg)
    const notification = JSON.parse(msg.body);
    // TODO: add notification

    this.messageService.findChatMessage(notification.id).then((result) => {
      console.log('Got message:', result)
      // Refresh page
      var sender: User = {
        id: result.senderId,
        displayName: result.senderName
      }
      var message: Message = {
        text: result.content,
        sender: sender,
        reciptient: this.currentUser
      }
      for(var i=0; i<this.channelList.length; i++){
        if(this.channelList[i].user.id == sender.id){
          this.channelList[i].messages.push(message);
          this.channelList[i].lastMessage = message.text;
        }
      }
    });
    this.scrollDown();
  };

  sendMessage() {
    console.log('sendMessage newMessage', this.newMessage);
    console.log('sendMessage currentReciptient', this.currentReciptient);

    if (this.newMessage.trim() !== "") {
      const message = {
        senderId: this.currentUser.id,
        recipientId: this.currentReciptient.id,
        senderName: this.currentUser.displayName,
        recipientName: this.currentReciptient.displayName,
        content: this.newMessage,
        timestamp: new Date(),
      };
      console.log('sendMessage!', message);
      this.stompClient.send("/app/chat", {}, JSON.stringify(message));

      // Refresh page
      var count = 0;
      var msg: Message = {
        text: this.newMessage,
        sender: this.currentUser,
        reciptient: this.currentReciptient
      }
      for(var i=0; i<this.channelList.length; i++){
        if(this.channelList[i].user.id == msg.reciptient.id){
          this.channelList[i].messages.push(msg);
          this.channelList[i].lastMessage = msg.text;
          count =  this.channelList[i].messages.length;
        }
      }

      // Clear text input
      this.newMessage = "";
    }
    this.scrollDown();
  }

  getMessages(id: string){
    for(var i=0; i<this.channelList.length; i++){
      if(this.channelList[i].user.id == id){
        this.currentReciptient = this.channelList[i].user
        this.messages = this.channelList[i].messages;
      }  
    }
    this.scrollDown();
  }

  loadContacts = () => {
    // Clear all channel
    this.channelList = [];

    // Request new data
    const promise = this.messageService.getUsers().then((users) =>
      users.map((channelUser) =>
        this.messageService.countNewMessages(channelUser.id, this.currentUser.id).then( (count) => {

          if(this.currentUser.id != channelUser.id){
            // TODO: remove count?
            console.log('count',count);

            // get all messages between two people
            this.findMessages(channelUser);
            console.log('this.channelList', this.channelList)

            channelUser.newMessages = count;
            //return channelUser;
          }
        })
      )
    );
  };

  findMessages(channelUser: User): Message[]{
    var messagesFound: Message[] = [];
    this.messageService.findChatMessages(this.currentUser.id, channelUser.id).then( result=>{
        console.log('findChatMessages_CurrentUser_User!', result);
        if(result.length != 0){
          for(var i=0; i<result.length; i++){
            var sender: User = {
              id: result[i].senderId,
              displayName: result[i].senderName
            }
            var message: Message = {
              text: result[i].content,
              sender: sender,
              reciptient: this.currentUser,
              time: new Date(result[i].timestamp)
            }
            messagesFound.push(message);
          }
        }

        // if(messagesFound.length == 0){
        //   var messageMock: Message = {
        //     text: "",
        //     sender: channelUser,
        //     reciptient: this.currentUser,
        //   }
        //   console.log('add mock message for:', channelUser);
        //   messagesFound[0] = messageMock;
        // }
    }).then(()=>{
      this.messageService.findChatMessages(channelUser.id, this.currentUser.id).then( result=>{
        console.log('findChatMessages_User_CurrentUser!', result);
        if(result.length != 0){
          for(var i=0; i<result.length; i++){
            var sender: User = {
              id: result[i].senderId,
              displayName: result[i].senderName
            }
            var message: Message = {
              text: result[i].content,
              sender: sender,
              reciptient: this.currentUser,
              time: new Date(result[i].timestamp)
            }
            messagesFound.push(message);
          }
        }
      }).then( final =>{
        if(messagesFound.length!=0){
          messagesFound.sort((a: Message, b: Message) => {
          var sortResult = a.time.getTime() - b.time.getTime();
          return sortResult;
        });
        }

        var lastMsg = "";
        if(messagesFound.length != 0){
          lastMsg = messagesFound[messagesFound.length-1].text
        }
        var channel: Channel = {
          user: channelUser,
          messages: messagesFound,
          lastMessage:lastMsg
        }
        this.channelList.push(channel);
      })
    })
    return messagesFound;
  }

  scrollDown(){
    // scroll down
    var scroll_msg_history = document.getElementById('msg_history');
    scroll_msg_history.scrollTop = scroll_msg_history.scrollHeight;
  }
}
