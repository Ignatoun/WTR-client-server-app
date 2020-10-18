import {getDetailedTasks} from "../routes/detailedTasksRoute";
import {DETAILED_TASKS_DELETE, DETAILED_TASKS_FAILED, DETAILED_TASKS_SUCCESS} from "../reducers/detailedTasksReducer";

export function detailedTasksAction(taskId, reportId, currentUser) {
    return async dispatch => {
        try {
            const arrayOfDetailedTasks = await getDetailedTasks(currentUser.token);
            const detailedTasks = {};
            detailedTasks[reportId] = arrayOfDetailedTasks.filter(value => value.taskId === +taskId);
            dispatch({
                type: DETAILED_TASKS_SUCCESS,
                payload: detailedTasks
            });
        } catch(e) {
            dispatch({
                type: DETAILED_TASKS_FAILED,
                payload: {}
            });
        }
    }
}

export function deleteDetailedTasks(index) {
    const detailedTasks = {};
    detailedTasks[index] = [];
    return {
        type: DETAILED_TASKS_DELETE,
        payload : detailedTasks,
    }
}

export function getAllDetailedTasks(currentUser) {
    return async dispatch => {
        try {
            const arrayOfDetailedTask = await getDetailedTasks(currentUser.token);
            dispatch({
                type: DETAILED_TASKS_SUCCESS,
                payload: {
                    detailedTasks: arrayOfDetailedTask,
                },
            });
        } catch(e) {
            dispatch({
                type: DETAILED_TASKS_DELETE,
                payload: {
                    detailedTasks: [],
                }
            });
        }
    }
}
