import { Injectable } from '@angular/core';
import { TokenStorageService } from '../LoginPage/services/token-storage.service';
import { User } from '../Schedule/schedule.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private tokenStorageService: TokenStorageService) {
    this.currentUser = this.tokenStorageService.getUser();
  };

  public CHAT_SERVICE = "http://localhost:8080";
  public stompClient;
  public msg = [];
  public currentUser: User;
  
  request = (options) => {
    const headers = new Headers();
  
    if (options.setContentType !== false) {
      headers.append("Content-Type", "application/json");
    }
  
    if (sessionStorage.getItem("auth-token")) {
      headers.append(
        "Authorization",
        "Bearer " + sessionStorage.getItem("auth-token")
      );
    }
  
    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);
  
    return fetch(options.url, options).then((response) =>
      response.json().then((json) => {
        if (!response.ok) {
          return Promise.reject(json);
        }
        return json;
      })
    );
  };

  findChatMessages(senderId, recipientId) {
    if (!sessionStorage.getItem("auth-token")) {
      return Promise.reject("No access token set.");
    }

    return this.request({
      url: this.CHAT_SERVICE + "/messages/" + senderId + "/" + recipientId,
      method: "GET",
    });
  }

  findChatMessage(id) {
    if (!sessionStorage.getItem("auth-token")) {
      return Promise.reject("No access token set.");
    }
  
    return this.request({
      url: this.CHAT_SERVICE + "/messages/" + id,
      method: "GET",
    });
  }

  getUsers() {
    // if (!localStorage.getItem("accessToken")) {
    //   return Promise.reject("No access token set.");
    // }
  
    return this.request({
      url: this.CHAT_SERVICE + "/api/user/all",
      method: "GET",
    });
  }

  countNewMessages(senderId, recipientId) {
    console.log('countNewMessages senderId', senderId)
    console.log('countNewMessages recipientId', recipientId)
    // if (!localStorage.getItem("accessToken")) {
    //   return Promise.reject("No access token set.");
    // }
  
    return this.request({
      url: this.CHAT_SERVICE + "/messages/" + senderId + "/" + recipientId + "/count",
      method: "GET",
    });
  }
}

