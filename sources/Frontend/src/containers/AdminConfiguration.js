import React from "react";
import {connect} from "react-redux";
import AdminConfigurationView from "../components/AdminConfigurationView";
import {deleteUser, usersAction} from "../actions/usersActions";

function AdminConfiguration (props) {
    return <AdminConfigurationView
        users={props.users}
        getUsers={props.getUsers}
        deleteUser={props.deleteUser}
    />
}

const mapStateToProps = store => {
    return {
        users: store.users,
    }
};

const mapFuncToProps = dispatch => {
    return {
        getUsers: (currentUser) => dispatch(usersAction(currentUser)),
        deleteUser: (users, userId, currentUser) => dispatch(deleteUser(users, userId, currentUser)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(AdminConfiguration);
