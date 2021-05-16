import { User } from "../../Schedule/schedule.model";

export class Channel {
    user: User
    messages: Message[]
    lastMessage: string
    status?: string // TODO: implement
}

// TODO: align with backend
export class Message {
    text: string
    sender: User
    reciptient: User
    read?: boolean // TODO: implement
    time?: Date
}