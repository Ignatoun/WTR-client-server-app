export const TITLES_FAILED = "TITLES_FAILED";
export const TITLES_SUCCESS = "TITLES_SUCCESS";

export const initialState = {
    titles: [],
};

export function titlesReducer(state = initialState, action) {
    switch (action.type) {
        case TITLES_FAILED:
            return {...state, ...action.payload };
        case TITLES_SUCCESS:
            return {...state, ...action.payload };
        default:
            return state;
    }
}
