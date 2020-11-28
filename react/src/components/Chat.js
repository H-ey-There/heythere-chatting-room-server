import React, { useState, useEffect, useRef} from 'react'
import Axios from 'axios';
import SockJsClient from 'react-stomp'

const Chat = (props) =>{
    let $websocket = useRef(null);

    const roomId = props.roomId;
    const setRoomId = props.setRoomId;
    const sender = props.sender;

    const [chat, setChat] = useState('')
    const [room, setRoom] = useState({
        host: '',
        users: [],
        name: ''
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
        if (recv.type === 'QUIT'){
            setRoomId('');
            return
        }
        setCount(recv.count)
        setMsg([
            {
                "message":recv.message
            }, ...msg])
        // setCount(recv.count);
    }

    const sendMessage = (e) => {
        if (chat !== ''){
            $websocket.current.sendMessage (
                '/pub/chat/message',
                JSON.stringify({type:'TALK', roomId:roomId, message:`${sender.name} : ${chat}` , userId:sender.userId
                }),
                {'Content-Type': 'application/json'}
            )
        }
        setChat('')
    }

    const onChange = (e) => {
        setChat(e.target.value);
      };

    const exit = () => {
        if (room.host === sender.userId) {
            $websocket.current.sendMessage(
                '/pub/chat/message',
                JSON.stringify({type: 'QUIT', roomId: roomId, message: '', userId:sender.userId}),
                {'Content-Type': 'application/json'})
        }else {
            $websocket.current.sendMessage(
                '/pub/chat/message',
                JSON.stringify({type: 'EXIT', roomId: roomId, message: sender.name+'이 퇴장하셨습니다.', userId:sender.userId}),
                {'Content-Type': 'application/json'})
        }
        setRoomId('')
    }
    
    return (
        <div> 
            <button onClick={exit}>뒤로가기</button>

            <h1>방제 : {room.name}</h1>
            <h2>시청자수 : {count}</h2>
            <h2>나 : {sender.name}</h2>

            <input type="text" name="name"  onChange={onChange} placeholder="여기에 메세지 입력"
                   value={chat}
                onKeyPress={e => {if (e.key === "Enter"){
                    sendMessage(e)
                }}}/>
            <button onClick={sendMessage}>보내기</button>

            <div>
                <ul>
                    {msg.map(m => <li>{m.message}</li>)}
                </ul>
            </div>

            <SockJsClient 
                url="http://localhost:8087/ws-stomp"
                topics={['/sub/chat/room/'+roomId]}
                onConnect={()=>{
                    $websocket.current.sendMessage (
                        '/pub/chat/message',
                        JSON.stringify({type:'ENTER', roomId:roomId, message:sender.name+'이 입장하였습니다.', userId:sender.userId}),
                        {'Content-Type': 'application/json'}
                    )
                }}
                onMessage={msg => recvMessage(msg)}
                ref={$websocket}/>
        </div>
    )
}


export default Chat

