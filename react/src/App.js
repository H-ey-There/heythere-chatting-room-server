import React from 'react'
import Chat from './components/Chat';
import Room from './components/Room';
import { useRecoilValue } from 'recoil';
import {RoomId} from './atoms'
import './App.css';


function App(){
  const roomId = useRecoilValue(RoomId)
  return (
    <div className="App">
      {roomId !== '' ? 
      <Chat/>
      : <Room/>}
    </div>
  )
}

export default App