export const LOCATIONS_FAILED = "LOCATION_FAILED";
export const LOCATIONS_SUCCESS = "LOCATION_SUCCESS";

export const initialState = {
    locations: [],
};

export function locationsReducer(state = initialState, action) {
    switch (action.type) {
        case LOCATIONS_FAILED:
            return { ...state, ...action.payload };
        case LOCATIONS_SUCCESS:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
