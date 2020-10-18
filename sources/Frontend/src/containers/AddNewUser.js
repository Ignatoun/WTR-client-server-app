import React from "react";
import {connect} from "react-redux";
import {addUser, usersAction} from "../actions/usersActions";
import AddNewUserView from "../components/AddNewUserView";
import {departmentsAction} from "../actions/departmentsAction";
import {titlesAction} from "../actions/titlesAction";

function AddNewUser (props) {
    return <AddNewUserView
        users={props.users}
        departments={props.departments.departments}
        titles={props.titles.titles}
        getUsers={props.getUsers}
        getDeparments={props.getDepartments}
        getTitles={props.getTitles}
        addUser={props.addUser}
    />
}

const mapStateToProps = store => {
    return {
        users: store.users,
        departments: store.departments,
        titles: store.titles,
    }
};

const mapFuncToProps = dispatch => {
    return {
        getUsers: (currentUser) => dispatch(usersAction(currentUser)),
        getDepartments: (currentUser) => dispatch(departmentsAction(currentUser)),
        getTitles: (currentUser) => dispatch(titlesAction(currentUser)),
        addUser: (users, user, currentUser) => dispatch(addUser(users, user, currentUser)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(AddNewUser);
