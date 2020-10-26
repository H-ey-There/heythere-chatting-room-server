import { atom } from "recoil"

export const RoomId = atom({
    key: 'roomId',
    default: ''
})

export const Chatting = atom({
    key: 'chatting',
    default: ''
})

export const Sender = atom({
    key: 'sender',
    default: {
        userId: '',
        name: 'Guest'}
})