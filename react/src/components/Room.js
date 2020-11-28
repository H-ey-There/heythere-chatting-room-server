import Axios from 'axios';
import React, {useState, useEffect, useRef} from 'react';
import SockJsClient from 'react-stomp'

const GET_ROOM_URL = 'http://localhost:8087/chat/rooms'
const USER_URL = 'http://localhost:8087/user/'

function Room(props) {
    const $websocket = useRef(null)

    const [roomList, setRoomList] = useState([])
    const [roomName, setRoomName] = useState('')

    const setRoomId = props.setRoomId;
    const sender = props.sender;
    const setSender = props.setSender;

    const fetchData = async () => {
        const response = await Axios.get(GET_ROOM_URL);
        if (response.data == null || response.data === []){
            setRoomList([])
            return
        }
        setRoomList(
            response.data.map(room =>
                <li key={room.roomId}
                    onClick={() => setRoomId(room.roomId)}>
                    {room.name}
                </li>)); // 데이터는 response.data 안에 들어있습니다.
    };

    const login = () => {
        const name = prompt("여기에 이름을 입력 ")
        Axios.post(USER_URL, {
            name : name
            }
        ).then(response=> {
            setSender(response.data);
        });

    }

    useEffect(() => {
        fetchData();
    }, []);

    const onChange = (e) => {
        setRoomName(e.target.value);
    };

    const createRoom = async () => {

        if (roomName === '') {
             setRoomName(sender.name + '의 방송')
        }

        await $websocket.current.sendMessage(
            "/pub/room",
            JSON.stringify({name: roomName, host: sender.userId}),
            {'Content-Type': 'application/json'})
    }

    return (
        <div>
            <h1>Hey, there!</h1>
            <h4>{sender.name}</h4>
            {sender.userId === '' ?
                <button onClick={login}>로그인</button> :
                <>
                <label> 방 제목 : &nbsp;
                    <input type="text" name="name" onKeyUp={onChange} placeholder="여기에 방제를 입력" onKeyPress={e => {
                        if (e.key === "Enter") {
                            createRoom()
                        }
                    }}/>
                </label>
                <button onClick={createRoom}>방송시작</button>
                </>
            }
            <ul>
                {roomList}
            </ul>
            <SockJsClient
                url="http://localhost:8087/ws-stomp"
                topics={['/sub/room/']}
                onMessage={
                    (msg) => {
                        if (sender.userId === msg.host)
                            setRoomId(msg.roomId);
                        fetchData()
                    }
                }
                ref={$websocket}/>
        </div>
    );
}

export default Room
