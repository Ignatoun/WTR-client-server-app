import React from "react";
import {Redirect} from "react-router-dom";
import {useParams} from "react-router-dom";
import {connect} from "react-redux";
import MyReportView from "../components/MyReportView";

function MyReport(props) {
    const params = useParams();
    return <MyReportView
        currentUser={props.currentUser}
        params={params}
    />
}

const mapStateToProps = store => {
    return {
        currentUser: store.users.currentUser
    }
};

export default connect(mapStateToProps)(MyReport);