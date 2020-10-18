import React from "react";
import {connect} from "react-redux";
import SearchingView from "../components/SearchingView";
import {reportDetailsAction} from "../actions/reportDetailsAction";
import {locationsAction} from "../actions/locationsAction";
import {projectsAction} from "../actions/projectsAction";
import {factorsAction} from "../actions/factorsAction";
import {featuresAction, getAllFeatures} from "../actions/featuresAction";
import {getAllTasks, tasksAction} from "../actions/tasksAction";
import {detailedTasksAction, getAllDetailedTasks} from "../actions/detailedTasksAction";
import DailyReportForm from "../components/DailyReportForm";


function Searching(props) {
    return (
        <SearchingView
            currentUser={props.currentUser}
            locations={props.locations}
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
            getDetailedTasks={props.getDetailedTasks}/>
    )
}

const mapStateToProps = store => {
    return {
        currentUser: store.users.currentUser,
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
        getLocations: (currentUser) => dispatch(locationsAction(currentUser)),
        getProjects: (currentUser) => dispatch(projectsAction(currentUser)),
        getFactors: (currentUser) => dispatch(factorsAction(currentUser)),
        getFeatures: (currentUser) => dispatch(getAllFeatures(currentUser)),
        getTasks: (currentUser) => dispatch(getAllTasks(currentUser)),
        getDetailedTasks: (currentUser) => dispatch(getAllDetailedTasks(currentUser)),
    }
};

export default connect(mapStateToProps, mapFuncToProps)(Searching);
