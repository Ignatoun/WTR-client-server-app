import {getTasks} from "../routes/tasksRoute";
import {TASKS_DELETE, TASKS_FAILED, TASKS_SUCCESS} from "../reducers/tasksReducer";
import {getFeatures} from "../routes/featuresRoute";
import {FEATURES_FAILED, FEATURES_SUCCESS} from "../reducers/featuresReducer";

export function tasksAction(featureId, reportId, currentUser) {
    return async dispatch => {
        try {
            const arrayOfTasks = await getTasks(currentUser.token);
            const tasks = {};
            tasks[reportId] = arrayOfTasks.filter(value => value.featureId === +featureId);
            dispatch({
                type: TASKS_SUCCESS,
                payload: tasks
            });
        } catch(e) {
            dispatch({
                type: TASKS_FAILED,
                payload: {}
            });
        }
    }
}

export function deleteTasks(index) {
    const tasks = {};
    tasks[index] = [];
    return {
        type: TASKS_DELETE,
        payload : tasks,
    }
}

export function getAllTasks(currentUser) {
    return async dispatch => {
        try {
            const arrayOfTasks = await getTasks(currentUser.token);
            dispatch({
                type: TASKS_SUCCESS,
                payload: {
                    tasks: arrayOfTasks,
                },
            });
        } catch(e) {
            dispatch({
                type: TASKS_FAILED,
                payload: {
                    tasks: [],
                }
            });
        }
    }
}
