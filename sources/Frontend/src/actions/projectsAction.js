import {getProjects} from "../routes/projectsRoute";
import {PROJECTS_FAILED, PROJECTS_SELECT, PROJECTS_SUCCESS} from "../reducers/projectsReducer";


export function projectsAction(currentUser) {
    return async dispatch => {
        try {
            const arrayOfProjects = await getProjects(currentUser.token);
            dispatch({
                type: PROJECTS_SUCCESS,
                payload: {
                    projects: arrayOfProjects,
                },
            });
        } catch(e) {
            dispatch({
                type: PROJECTS_FAILED,
                payload: {
                    projects: [],
                }
            });
        }
    }
}

export function selectProjectAction(projects, projectId, reportId) {
    const project = {};
    project[reportId] = projects.find(value => value.projectId === +projectId);
    return {
        type: PROJECTS_SELECT,
        payload: project,
    };
}

export function deleteProjects(index) {
    const project = {};
    project[index] = [];
    return {
        type: PROJECTS_SELECT,
        payload: project,
    };
}

