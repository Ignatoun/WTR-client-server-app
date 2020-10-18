export const FACTORS_FAILED = "FACTORS_FAILED";
export const FACTORS_SUCCESS = "FACTORS_SUCCESS";

export const initialState = {
    factors: [],
};

export function factorsReducer(state = initialState, action) {
    switch (action.type) {
        case FACTORS_FAILED:
            return { ...state, ...action.payload };
        case FACTORS_SUCCESS:
            return { ...state, ...action.payload };
        default:
            return state;
    }
}
