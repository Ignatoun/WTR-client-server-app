import React from "react";
import {connect} from "react-redux";
import {allReportDetails,updateReportDetail,getAllReportDetailsRejected,getReportDetailId,putNewReportDetails} from "../actions/reportDetailsAction";
import ManagerConfigurationView from "../components/ManagerConfigurationView"
import {getAllUsersAction,managerUser} from "../actions/usersActions"
import {locationsAction} from "../actions/locationsAction";
import {projectsAction} from "../actions/projectsAction";
import {factorsAction} from "../actions/factorsAction";
import {featuresAction, getAllFeatures} from "../actions/featuresAction";
import {getAllTasks, tasksAction} from "../actions/tasksAction";
import {detailedTasksAction, getAllDetailedTasks} from "../actions/detailedTasksAction";


function ManagerConfiguration(props) {
    return  <ManagerConfigurationView
            getReports={props.getReports}
            reportDetails={props.reportDetails}
            updateReportDetail={props.updateReportDetail}
            getAllUsersAction={props.getAllUsersAction}
            users={props.users}
            managerUser={props.managerUser}
            allReportDetailsForRegistered={props.allReportDetailsForRegistered}
            getAllReportDetailsRejected={props.getAllReportDetailsRejected}
            getReportDetailId={props.getReportDetailId}
            getFactors={props.getFactors}
            getLocations={props.getLocations}
            getProjects={props.getProjects}
            getFeatures={props.getFeatures}
            getTasks={props.getTasks}
            getDetailedTasks={props.getDetailedTasks}
            projects={props.projects}
            features={props.features}
            tasks={props.tasks}
            detailedTasks={props.detailedTasks}
            locations={props.locations}
            factors={props.factors.factors}
            currentUser={props.currentUser}
            putNewReportDetails={props.putNewReportDetails}
            getReportDetailId={props.getReportDetailId}
    />
}

const mapStateToProps = store => {
    return {
        currentUser: store.users.currentUser,
        reportDetails: store.reportDetails,
        users: store.users,
        projects: store.projects.projects,
        features: store.features.features,
        tasks: store.tasks.tasks,
        detailedTasks: store.detailedTasks.detailedTasks,
        locations: store.locations.locations,
        factors: store.factors,
    }
};

const mapFuncToProps = dispatch => {
    return {
        getReports: () => dispatch(allReportDetails()),
        updateReportDetail:(reportDetails, reportDetail) => dispatch(updateReportDetail(reportDetails, reportDetail)),
        getAllUsersAction: (currentUser) => dispatch(getAllUsersAction(currentUser)),
        managerUser: (userId) => dispatch(managerUser(userId)),
        getAllReportDetailsRejected: (currentUser) => dispatch(getAllReportDetailsRejected(currentUser)),
        getReportDetailId: (reportDetailId) => dispatch(getReportDetailId(reportDetailId)),

        getLocations: (currentUser) => dispatch(locationsAction(currentUser)),
        getProjects: (currentUser) => dispatch(projectsAction(currentUser)),
        getFactors: (currentUser) => dispatch(factorsAction(currentUser)),
        getFeatures: (currentUser) => dispatch(getAllFeatures(currentUser)),
        getTasks: (currentUser) => dispatch(getAllTasks(currentUser)),
        getDetailedTasks: (currentUser) => dispatch(getAllDetailedTasks(currentUser)),

        putNewReportDetails:(reports) => dispatch(putNewReportDetails(reports)),

        getReportDetailId: (reportDetailId) => dispatch(getReportDetailId(reportDetailId)),
    }
};


export default connect(mapStateToProps, mapFuncToProps)(ManagerConfiguration);