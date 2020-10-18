import React from "react";
import {useParams} from "react-router-dom";
import DailyReportForm from "../components/DailyReportForm";
import {connect} from "react-redux";
import {locationsAction} from "../actions/locationsAction";
import {deleteProjects, projectsAction} from "../actions/projectsAction";
import {factorsAction} from "../actions/factorsAction";
import {deleteFeature, featuresAction} from "../actions/featuresAction";
import {deleteTasks, tasksAction} from "../actions/tasksAction";
import {deleteDetailedTasks, detailedTasksAction} from "../actions/detailedTasksAction";
import {selectProjectAction} from "../actions/projectsAction";
import {reportDetailsAction, reportDetailsCopy} from "../actions/reportDetailsAction";


function DailyReport(props) {
    const params = useParams();
    return <DailyReportForm
        params={params}
        currentUser={props.currentUser}
        reportDetails={props.reportDetails}
        locations={props.locations.locations}
        projects={props.projects}
        factors={props.factors.factors}
        getFactors={props.getFactors}
        features={props.features}
        tasks={props.tasks}
        getLocations={props.getLocations}
        getProjects={props.getProjects}
        detailedTasks={props.detailedTasks}
        getFeatures={props.getFeatures}
        getTasks={props.getTasks}
        getDetailedTasks={props.getDetailedTasks}
        selectProject={props.selectProject}
        deleteFeatures={props.deleteFeatures}
        deleteTasks={props.deleteTasks}
        deleteDetailedTasks={props.deleteDetailedTasks}
        deleteProjects={props.deleteProjects}
        copyReportDetails={props.copyReportDetails}
        getReports={props.getReports}
    />
}

const mapStateToProps = (store) => {
    return {
        currentUser: store.users.currentUser,
        reportDetails: store.reportDetails,
        locations: store.locations,
        projects: store.projects,
        factors: store.factors,
        features: store.features,
        tasks: store.tasks,
        detailedTasks: store.detailedTasks,
    }
};

const mapFuncToProps = (dispatch) => {
    return {
        getReports: (currentDate, currentUser) => dispatch(reportDetailsAction(currentDate, currentUser)),
        getLocations: (currentUser) => dispatch(locationsAction(currentUser)),
        getProjects: (currentUser) => dispatch(projectsAction(currentUser)),
        getFactors: (currentUser) => dispatch(factorsAction(currentUser)),
        getFeatures: (projectId, index, currentUser) => dispatch(featuresAction(projectId, index, currentUser)),
        getTasks: (featureId, index, currentUser) => dispatch(tasksAction(featureId, index, currentUser)),
        getDetailedTasks: (taskId, index, currentUser) => dispatch(detailedTasksAction(taskId, index, currentUser)),
        selectProject: (projects, projectId, index) => dispatch(selectProjectAction(projects, projectId, index)),
        deleteFeatures: (index) => dispatch(deleteFeature(index)),
        deleteTasks: (index) => dispatch(deleteTasks(index)),
        deleteDetailedTasks: (index) => dispatch(deleteDetailedTasks(index)),
        deleteProjects: (index) => dispatch(deleteProjects(index)),
        copyReportDetails: (report) => dispatch(reportDetailsCopy(report)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(DailyReport);
