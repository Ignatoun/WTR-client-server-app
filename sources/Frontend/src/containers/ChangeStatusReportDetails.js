import React from "react";
import {connect} from "react-redux";
import {allReportDetails,updateReportDetail,getReportDetailId} from "../actions/reportDetailsAction";
import ChangeStatusReportDetailsView from "../components/changeStatusReportDetailsView"
import {getAllUsersAction} from "../actions/usersActions"


function ChangeStatusReportDetails(props) {
    return  <ChangeStatusReportDetailsView
            getReportsByUser={props.getReportsByUser}
            reportDetails={props.reportDetails}
            updateReportDetail={props.updateReportDetail}
            getAllUsersAction={props.getAllUsersAction}
            users={props.users}
            getReportDetailId={props.getReportDetailId}

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
        getReportsByUser: (userId,currentUser) => dispatch(allReportDetails(userId,currentUser)),
        updateReportDetail:(reportDetails, reportDetail) => dispatch(updateReportDetail(reportDetails, reportDetail)),
        getAllUsersAction: () => dispatch(getAllUsersAction()),
        getReportDetailId: (reportDetailId) => dispatch(getReportDetailId(reportDetailId))
    }
};

export default connect(mapStateToProps, mapFuncToProps)(ChangeStatusReportDetails);