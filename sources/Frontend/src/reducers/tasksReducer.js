export const TASKS_FAILED = "TASKS_FAILED";
export const TASKS_SUCCESS = "TASKS_SUCCESS";
export const TASKS_DELETE = "TASKS_DELETE";

export const initialState = {};

export function tasksReducer(state = initialState, action) {
    switch (action.type) {
        case TASKS_FAILED:
            return { ...state, ...action.payload };
        case TASKS_SUCCESS:
            return { ...state, ...action.payload };
        case TASKS_DELETE:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
