import React, { useState } from 'react';
import alarmGo from '../images/bell.png'
import receiptGo from '../images/receipt.png'
import nowGo from '../images/short_cut.png'
import logoWhite from '../images/tier_logo_white.png'
import imgPhone from '../images/ned_phone.png'
import qrPay from '../images/qr_button_black.png'
import Modal from '../util/Modal'
import KhApi from '../api/khApi';

const GoHome = () => {
    const localId = window.localStorage.getItem("userId");
    const localPw = window.localStorage.getItem("userPw");
    const isLogin = window.localStorage.getItem("isLogin");
    if(isLogin === "FALSE") window.location.replace("/");

    const [modalOpen, setModalOpen] = useState(false);

    const closeModal = () => {
        setModalOpen(false);
    };

    const confirmModal = async() => {
        setModalOpen(false);
        const memberReg = await KhApi.memberDelete(localId);
        console.log(memberReg.data.result);
        if(memberReg.data.result === "OK") {
            window.location.replace("/");
        } else {

        }
    };
    
    const onClickWallet = async() => {
    }

    const onClickEFT = () => {
        console.log("EFT로 이동");
        window.location.replace("/GoEFT");
    }

    const onClickMemberReg = () => {
        console.log("회원 가입으로 이동");
        window.location.replace("/Signup");
    }

    const onClickMemberDelete = () => {
        setModalOpen(true);
    }

    const onClickLogout = () => {
        console.log("Logout 추가");
        window.localStorage.setItem("userId", "");
        window.localStorage.setItem("userPw", "");
        window.localStorage.setItem("isLogin", "FALSE");
        window.location.replace("/");
    }

    const onClickMember = () => {
        console.log("회원정보로 이동");
        window.location.replace("/MemberInfo");
    }

    return(
        <div>
            <div className="container">
                <div className="mainhead">
                    <div className="logo2">
                        <img src={logoWhite} alt="White" />
                    </div>
                    <div className="bell">
                        <img src={alarmGo} alt="alarm" />
                    </div>
                </div>
                <div className="linkwallet" onClick={onClickWallet}>
                    <img src={imgPhone} className="nedlogo" alt="bigN" />
                        <span className="linkwallet1">There is no wallet connected.</span>
                        <span className="linkwallet2">+ Add Wallet</span>
                </div>
                <div className="EFT" onClick={onClickMember}>
                    <img src={nowGo} className="imgEFT" alt="GoEFT" />
                    <span className="EFTtypo">회원정보 조회</span>
                </div>
                <div className="ATM" onClick={onClickMemberReg}>
                    <img src={receiptGo} className="imgATM" alt="onClickMemberReg" />
                    <span className="ATMtypo">회원 추가</span>
                </div>
                <div className="Peer" onClick={onClickMemberDelete}>
                    <img src={nowGo} className="imgPeer" alt="GoPeer" />
                    <span className="Peertypo">회원 탈퇴</span>
                </div>
                <div className="QR" onClick={onClickLogout}>
                    <img src={qrPay} className="imgQrblack" alt="GoQrpay" />
                    <span className="QRtypo">로그아웃</span>
                </div>
                <div className="history" >
                   <p>회원 아이디 : {localId}</p>
                   <p>회원 패스워드 : {localPw}</p>
                </div>
            </div>
            {modalOpen && <Modal open={modalOpen} confirm={confirmModal} close={closeModal} type={true} header="확인">정말 탈퇴하시겠습니까?</Modal>}
        </div>
    )
};

export default GoHome;