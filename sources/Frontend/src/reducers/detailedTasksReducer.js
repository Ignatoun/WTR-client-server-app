export const DETAILED_TASKS_FAILED = "DETAILED_TASKS_FAILED";
export const DETAILED_TASKS_SUCCESS = "DETAILED_TASKS_SUCCESS";
export const DETAILED_TASKS_DELETE = "DETAILED_TASKS_DELETE";

export const initialState = {};

export function detailedTasksReducer(state = initialState, action) {
    switch (action.type) {
        case DETAILED_TASKS_FAILED:
            return { ...state, ...action.payload };
        case DETAILED_TASKS_SUCCESS:
            return { ...state, ...action.payload };
        case DETAILED_TASKS_DELETE:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
