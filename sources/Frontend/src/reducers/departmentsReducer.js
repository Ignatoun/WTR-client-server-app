export const DEPARTMENTS_FAILED = "DEPARTMENTS_FAILED";
export const DEPARTMENTS_SUCCESS = "DEPARTMENTS_SUCCESS";

export const initialState = {
    departments: [],
};

export function departmentsReducer(state = initialState, action) {
    switch (action.type) {
        case DEPARTMENTS_FAILED:
            return {...state, ...action.payload };
        case DEPARTMENTS_SUCCESS:
            return {...state, ...action.payload };
        default:
            return state;
    }
}
