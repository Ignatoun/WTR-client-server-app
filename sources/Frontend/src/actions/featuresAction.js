import {getFeatures} from "../routes/featuresRoute";
import {FEATURES_DELETE, FEATURES_FAILED, FEATURES_SUCCESS} from "../reducers/featuresReducer";
import {getDetailedTasks} from "../routes/detailedTasksRoute";
import {DETAILED_TASKS_DELETE, DETAILED_TASKS_SUCCESS} from "../reducers/detailedTasksReducer";

export function featuresAction(projectId, reportId, currentUser) {
    return async dispatch => {
        try {
            const arrayOfFeatures = await getFeatures(currentUser.token);
            const features = {};
            features[reportId] = arrayOfFeatures.filter(value => value.projectId === +projectId);
            dispatch({
                type: FEATURES_SUCCESS,
                payload: features,
            });
        } catch (e) {
            dispatch({
                type: FEATURES_FAILED,
                payload: {}
            });
        }
    }
}

export function deleteFeature(index) {
    const features = {};
    features[index] = [];
    return {
        type: FEATURES_DELETE,
        payload : features,
    }
}

export function getAllFeatures(currentUser) {
    return async dispatch => {
        try {
            const arrayOfFeatures = await getFeatures(currentUser.token);
            dispatch({
                type: FEATURES_SUCCESS,
                payload: {
                    features: arrayOfFeatures,
                },
            });
        } catch(e) {
            dispatch({
                type: FEATURES_FAILED,
                payload: {
                    features: [],
                }
            });
        }
    }
}
