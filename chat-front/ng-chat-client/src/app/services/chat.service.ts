import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { ChatMessage } from '../model/chat-message';
import { BehaviorSubject, map } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient: any
  private messageSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);
  //BehaviorSubject sirve para tener un valor inicial y poder emitir valores a los suscriptores de la clase ChatService
  constructor(private http : HttpClient) { 
    this.initConnenctionSocket();
  }

  initConnenctionSocket() {
    const url = '//localhost:8080/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
  }

  joinRoom(roomId: string) {//al esntra a una sala se suscribe a un topico y se conecta al servidor de stomp
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const currentMessage = this.messageSubject.getValue();
        currentMessage.push(messageContent);
        console.log("Mensaje guardado del topic: ");
        console.log(messages);
        this.messageSubject.next(currentMessage);
      })
    })
    this.loadMessages(roomId);
  }

  sendMessage(roomId: string, chatMessage: ChatMessage) {
    this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage))
  }

  getMessageSubject(){
    return this.messageSubject.asObservable();
  }

  loadMessages(roomId: string) {
    this.http.get<any[]>(`http://localhost:8080/api/chat/${roomId}`).pipe(
      map(result => {
        return result.map(r => {
          return {
            message: r.message,
            user: r.userName
          } as ChatMessage
        })
      })).subscribe({
        next: (chatMessages : ChatMessage[]) => {
          this.messageSubject.next(chatMessages);
        },
        error: (error) => {
          console.log(error);
        }
      })
  }

  // topicOut(roomId: string) {
  //   this.stompClient.unsubscribe(`/topic/${roomId}`);
  // }

  /*
  Aplica lo mismo para actualizar los mensajes usa un listener de tipo  BehaviorSubject, algo como esto:
  //Llama este metodo desde tu contructor o el ngOnInit
  lisenerBagde() {
    this.chatService.getMessageSubject().subscribe((messages: any) => {
     // Aca recibes el valor de contador
    });
  } */
}
