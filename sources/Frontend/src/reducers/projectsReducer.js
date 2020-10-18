export const PROJECTS_FAILED = "PROJECTS_FAILED";
export const PROJECTS_SUCCESS = "PROJECTS_SUCCESS";
export const PROJECTS_SELECT = "PROJECTS_SELECT";
export const PROJECT_DELETE = "PROJECT_DELETE";

export const initialState = {
    projects: [],
};

export function projectsReducer(state = initialState, action) {
    switch (action.type) {
        case PROJECTS_SUCCESS:
            return { ...state, ...action.payload };
        case PROJECTS_FAILED:
            return { ...state, ...action.payload };
        case PROJECTS_SELECT:
            return { ...state, ...action.payload };
        case PROJECT_DELETE:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
