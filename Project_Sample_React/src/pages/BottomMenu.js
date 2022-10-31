import styled from 'styled-components';


const BottomMenu = () => {
    const isLoginStr = window.localStorage.getItem("isLogin");
    

    const BottomMenuContainer = styled.div`
        display: flex;
        width: auto;
        position: fixed;
        bottom: 0;
        width: 100%;
        justify-content: space-around;
    `;

    const BottomMenuBtn = styled.button`
        margin: 4px;
        flex-grow: 1;
        width: 100px;
        height: 100px;
        background-color: bisque;
        border-radius: 10px;
    `;

    const onClickHome = () => {
        window.location.replace("/home");
    }
    const onClickInfo = () => {
        window.location.replace("/MemberInfo");
    }
    if(isLoginStr ==="TRUE")  {
        return (
            <BottomMenuContainer>
                <BottomMenuBtn onClick={onClickHome}>Home</BottomMenuBtn>
                <BottomMenuBtn onClick={onClickInfo}>Info</BottomMenuBtn>
                <BottomMenuBtn>Button3</BottomMenuBtn>
                <BottomMenuBtn>Button4</BottomMenuBtn>
            </BottomMenuContainer>
        );
    }
    
}
export default BottomMenu;