export const USERS_REQUEST = "USERS_REQUEST";
export const USERS_FAILED = "USERS_FAILED";
export const USERS_SUCCESS = "USERS_SUCCESS";
export const USERS_DELETE = "USERS_DELETE";
export const USERS_ADD = "USERS_ADD";
export const USERS_UPDATE = "USERS_UPDATE";
export const USERS_AUTH = "USERS_AUTH";


export const initialState = {
    allUsers: [],
    currentUser: {},
    loading: false,
    message: "",
    managerUser: null
};

export function usersReducer(state = initialState, action) {
    switch (action.type) {
        case USERS_REQUEST:
            return { ...state, ...action.payload };
        case USERS_FAILED:
            return { ...state, ...action.payload };
        case USERS_SUCCESS:
            return {...state, ...action.payload };
        case USERS_DELETE:
            return {...state, ...action.payload };
        case USERS_ADD:
            return {...state, ...action.payload };
        case USERS_UPDATE:
            return {...state, ...action.payload };
        case USERS_AUTH:
            return {...state, ...action.payload };
        default:
            return state;
    }
}
