import React, { useState, useEffect, useRef} from 'react'
import Axios from 'axios';
import {useRecoilState, useRecoilValue} from 'recoil';
import {RoomId, Sender} from '../atoms'
import SockJsClient from 'react-stomp'

function Chat(){
    let $websocket = useRef(null);

    const [roomId, setRoomId] = useRecoilState(RoomId)
    const sender = useRecoilValue(Sender);

    const [chat, setChat] = useState('')
    const [room, setRoom] = useState({
        host: '',
        users: [],
        name: '',
    })

    const [count, setCount] = useState(0);

    const [msg, setMsg] = useState([])

    const updateRoom = async () =>{
        if (roomId !== ''){
            await Axios.get(`http://localhost:8087/chat/room/${roomId}`)
                .then(res=> {
                    setRoom(res.data)
                });
        }

    }

    useEffect(()=>{
        updateRoom()
      },[]);
    
    const recvMessage = (recv) => {
        updateRoom()

        if (recv.type === 'HOST'){
            setRoomId('')
            return
        }
        setMsg([
            {
                "type":recv.type,
                "sender":(recv.type === 'ENTER' || recv.type === 'EXIT')  ? '[알림]' : recv.sender,
                "message":recv.message
            },
            ...msg])
        setCount(recv.count);
        console.log(count)
    }

    const sendMessage = (e) => {
        if (chat !== ''){
            $websocket.current.sendMessage (
                '/pub/chat/message',
                JSON.stringify({type:'TALK', roomId:roomId, sender:sender.userId, message:chat}),
                {'Content-Type': 'application/json'}
            )
        }
    }

    const onChange = (e) => {
        setChat(e.target.value);
      };

    const exit = () => {
        if (room.host === sender.userId) {
            $websocket.current.sendMessage(
                '/pub/chat/message',
                JSON.stringify({type: 'HOST', roomId: roomId, sender: sender.userId, message: ''}),
                {'Content-Type': 'application/json'})
        }else {
            $websocket.current.sendMessage(
                '/pub/chat/message',
                JSON.stringify({type: 'EXIT', roomId: roomId, sender: sender.userId, message: ''}),
                {'Content-Type': 'application/json'})
        }
        setRoomId('')
    }
    
    return (
        <div> 
            <button onClick={exit}>뒤로가기</button>

            <h1>방제 : {room.name}</h1>
            <h2>나 : {sender.name}</h2>

            <input type="text" name="name"  onKeyUp={onChange} placeholder="여기에 메세지 입력" 
                onKeyPress={e => {if (e.key === "Enter"){
                    sendMessage(e)
                }}}/>
            <button onClick={sendMessage}>보내기</button>

            <div>
                <ul>
                    {msg.map(m => <li>{m.sender}: {m.message}</li>)}
                </ul>
            </div>
            <div>
                시청자수 : {count}
            </div>

            <SockJsClient 
                url="http://localhost:8087/ws-stomp"
                topics={['/sub/chat/room/'+roomId]}
                onConnect={()=>{
                    $websocket.current.sendMessage (
                        '/pub/chat/message',
                        JSON.stringify({type:'ENTER', roomId:roomId, sender:sender.userId, message:''}),
                        {'Content-Type': 'application/json'}
                    )
                }}
                onMessage={msg => recvMessage(msg)}
                ref={$websocket}/>
        </div>
    )
}


export default Chat


