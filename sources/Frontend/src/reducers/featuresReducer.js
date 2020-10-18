export const FEATURES_FAILED = "FEATURES_FAILED";
export const FEATURES_SUCCESS = "FEATURES_SUCCESS";
export const FEATURES_DELETE = "FEATURES_DELETE";

export const initialState = {};

export function featuresReducer(state = initialState, action) {
    switch (action.type) {
        case FEATURES_FAILED:
            return { ...state, ...action.payload };
        case FEATURES_SUCCESS:
            return { ...state, ...action.payload };
        case FEATURES_DELETE:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
