import React from "react";
import {connect} from "react-redux";
import {reportDetailsAction} from "../actions/reportDetailsAction";
import CalendarView from "../components/CalendarView";


function Calendar(props) {
    return (
        <CalendarView
            currentUser={props.currentUser}
            reportDetails={props.reportDetails}
            getReports={props.getReports}/>
    )
}

const mapStateToProps = store => {
    return {
        currentUser: store.users.currentUser,
        reportDetails: store.reportDetails,
    }
};

const mapFuncToProps = dispatch => {
    return {
        getReports: (currentDate, currentUser) => dispatch(reportDetailsAction(currentDate, currentUser)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(Calendar);
