import React from 'react';
import '../App';
import './Modal.css';

const Modal = (props) => {
    const { open, confirm, close, type, header } = props;
    return (
        <div className={open ? 'openModal modal' : 'modal'}>
            {open && 
                <section>
                    <header>
                        {header}
                        <button onClick={close}>
                            &times;
                        </button>
                    </header>
                    <main>{props.children}</main>
                    <footer>
                        {type && <button onClick={confirm}>확인</button>}
                        <button onClick={close}>취소</button>
                    </footer>
                </section>
            }
        </div>
    );
};
export default Modal;