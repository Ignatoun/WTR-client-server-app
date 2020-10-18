import React from "react";
import {connect} from "react-redux";
import LoginForm from "../components/LoginForm";
import {userAuthorization} from "../actions/usersActions";


function Login(props) {
    return (
        <LoginForm
            authorization={props.authorization}
            users={props.users}
        />
    )
}

const mapStateToProps = store => {
    return {
        users: store.users,
    }
};

const mapFuncToProps = dispatch => {
    return {
        authorization: user => dispatch(userAuthorization(user)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(Login);
