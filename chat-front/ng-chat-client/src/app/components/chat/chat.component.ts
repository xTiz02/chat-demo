import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
//import for ngModel
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';
import { ActivatedRoute } from '@angular/router';
import { ChatMessage } from '../../model/chat-message';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [ CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {
  messageInput: string = '';
  userId: string="";
  messageList: any[] = [];

  constructor(private chatService: ChatService,
    private route: ActivatedRoute
    ){

  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params["userId"];
    this.chatService.joinRoom("ABC");
    this.lisenerMessage();
  }

  sendMessage() {
    const chatMessage = {
      message: this.messageInput,
      user: this.userId
    }as ChatMessage
    this.chatService.sendMessage("ABC", chatMessage);
    this.messageInput = '';
  }

  lisenerMessage() {
    this.chatService.getMessageSubject().subscribe((messages: any) => {
      this.messageList = messages.map((item: any)=> ({
        ...item,
        message_side: item.user === this.userId ? 'sender': 'receiver'
      }))
      console.log(this.messageList);
    });
    
  }
}
