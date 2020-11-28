import React, {useState} from 'react'
import Chat from './components/Chat';
import Room from './components/Room';
import './App.css';

function App(){
  const [roomId,setRoomId] = useState('');
  const [sender, setSender] = useState(
      {
        userId:'',
        name:'GUEST'
      }
  )
  return (
    <div className="App">
      {roomId !== '' ? 
      <Chat roomId={roomId} setRoomId={setRoomId}
            sender={sender}/>
      : <Room setRoomId={setRoomId}
              sender={sender} setSender={setSender}/>}
    </div>
  )
}

export default App