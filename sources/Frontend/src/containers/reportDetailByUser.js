import React from "react";
import {connect} from "react-redux";
import {allReportDetails,updateReportDetail} from "../actions/reportDetailsAction";
import ReportDetailByUserView from "../components/reportDetailByUserView"



function ReportDetailByUser(props) {
    return  <ReportDetailByUserView
            getReports={props.getReports}
            reportDetails={props.reportDetails}
            updateReportDetail={props.updateReportDetail}
            users={props.users}
            getReportsByUser={props.getReportsByUser}
    />
}

const mapStateToProps = store => {
    return {
        reportDetails: store.reportDetails,
        users: store.users,
    }
};

const mapFuncToProps = dispatch => {
    return {
        getReports: () => dispatch(allReportDetails()),
        updateReportDetail:(reportDetails, reportDetail,token,x) => dispatch(updateReportDetail(reportDetails, reportDetail,token,x)),
        getReportsByUser: (userId,currentUser) => dispatch(allReportDetails(userId,currentUser)),
    }
};


export default connect(mapStateToProps, mapFuncToProps)(ReportDetailByUser);
