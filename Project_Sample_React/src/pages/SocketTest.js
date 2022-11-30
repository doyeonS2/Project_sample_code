import React, { useEffect, useState, useRef } from "react";
import '../App.css'

const SocketTest = () => {
    const [socketConnected, setSocketConnected] = useState(false);
    const [inputMsg, setInputMsg] = useState("");
    const [rcvMsg, setRcvMsg] = useState("");
    const webSocketUrl = `ws://localhost:8211/ws/chat`;
    const roomId = window.localStorage.getItem("chatRoomId");
    const sender = "곰돌이사육사";
    let ws = useRef(null);
    const [items, setItems] = useState([]);

    const onChangMsg = (e) => {
        setInputMsg(e.target.value)
    }

    const onEnterKey = (e) => {
        if(e.key === 'Enter') onClickMsgSend(e);
    }

    const onClickMsgSend = (e) => {
        e.preventDefault();
        ws.current.send(
            JSON.stringify({
            "type":"TALK",
            "roomId": roomId,
            "sender": sender,
            "message":inputMsg}));
            setInputMsg("");
    }
    const onClickMsgClose = () => {
        ws.current.send(
            JSON.stringify({
            "type":"CLOSE",
            "roomId": roomId,
            "sender":sender,
            "message":"종료 합니다."}));
        ws.current.close();
    }

    useEffect(() => {
        console.log("방번호 : " + roomId);
        if (!ws.current) {
            ws.current = new WebSocket(webSocketUrl);
            ws.current.onopen = () => {
                console.log("connected to " + webSocketUrl);
            setSocketConnected(true);
            };
        }
        if (socketConnected) {
            ws.current.send(
                JSON.stringify({
                "type":"ENTER",
                "roomId": roomId,
                "sender": sender,
                "message":"처음으로 접속 합니다."}));
        }
        ws.current.onmessage = (evt) => {
            const data = JSON.parse(evt.data);
            console.log(data.message);
            setRcvMsg(data.message);
            setItems((prevItems) => [...prevItems, data]);
      };
    }, [socketConnected]);

    return (
        <div >
            <div>socket</div>
            <div>socket connected : {`${socketConnected}`}</div>
            <div>방번호: {roomId}</div>
            <h2>소켓으로 문자 전송하기 테스트</h2>
            <div>
                {items.map((item) => {
                return <div>{`${item.sender} > ${item.message}`}</div>;
                })}
            </div>
            <input className="msg_input" placeholder="문자 전송" value ={inputMsg} onChange={onChangMsg} onKeyUp={onEnterKey}/>
            <button className="msg_send" onClick={onClickMsgSend}>전송</button>
            <p/>
            <button className="msg_close" onClick={onClickMsgClose}>채팅 종료 하기</button>
        </div>
      );
    };
    
    export default SocketTest;