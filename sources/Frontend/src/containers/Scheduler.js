import React from "react";
import {connect} from "react-redux";
import SchedulerView from "../components/SchedulerView";

function Scheduler(props) {
    return <SchedulerView
        currentUser={props.currentUser}/>;
}

const mapStateToProps = store => {
    return {
        currentUser: store.users.currentUser,
    };
};

export default connect(mapStateToProps)(Scheduler);