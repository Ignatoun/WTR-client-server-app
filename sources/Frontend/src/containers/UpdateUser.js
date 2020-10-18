import React from "react";
import {connect} from "react-redux";
import {useParams} from "react-router-dom";
import {updateUser, usersAction} from "../actions/usersActions";
import {departmentsAction} from "../actions/departmentsAction";
import {titlesAction} from "../actions/titlesAction";
import UpdateUserView from "../components/UpdateUserView";

function UpdateUser (props) {
    const params = useParams();
    return <UpdateUserView
        params={params}
        users={props.users}
        departments={props.departments.departments}
        titles={props.titles.titles}
        getUsers={props.getUsers}
        getDeparments={props.getDepartments}
        getTitles={props.getTitles}
        updateUser={props.updateUser}
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
        updateUser: (users, user, currentUser) => dispatch(updateUser(users, user, currentUser)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(UpdateUser);
