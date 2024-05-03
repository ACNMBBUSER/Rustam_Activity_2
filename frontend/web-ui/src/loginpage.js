import React from 'react'
import { useNavigate } from 'react-router-dom'

const LoginPage = (props) => {
    const { userName, logIn } = props;
    const navigate = useNavigate();

    const onButtonClick = () => {
        navigate('/login');
        console.log('Button clicked');
    };

    return (
        <div className="mainContainer">
            <div className={'titleContainer'}>
                <div>Welcome!</div>
            </div>
            <div>This is the home page.</div>
            <div className={'buttonContainer'}>
                <input
                    className={'inputButton'}
                    type="button"
                    onClick={onButtonClick}
                    value={logIn ? 'Log out' : 'Log in'}
                />
                {logIn ? <div>Your username is {userName}</div> : null}
            </div>
        </div>
    );
};

export default LoginPage;