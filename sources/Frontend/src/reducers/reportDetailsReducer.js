export const REPORT_DETAILS_REQUEST = "REPORT_DETAILS_REQUEST";
export const REPORT_DETAILS_FAILED = "REPORT_DETAILS_FAILED";
export const REPORT_DETAILS_SUCCESS = "REPORT_DETAILS_SUCCESS";
export const REPORT_DETAILS_COPY = "REPORT_DETAILS_COPY";
export const REPORT_DETAILS_UPDATE = "REPORT_DETAILS_UPDATE";

export const initialState = {
    currentMonth: [],
    copy: {},
    request: false,
    loading:false,
    allReports:[],
    detailsId: null,

};

export function reportDetailsReducer(state = initialState, action) {
    switch (action.type) {
        case REPORT_DETAILS_REQUEST:
            return { ...state, ...action.payload };
        case REPORT_DETAILS_FAILED:
            return { ...state, ...action.payload };
        case REPORT_DETAILS_SUCCESS:
            return {...state, ...action.payload };
        case REPORT_DETAILS_COPY:
            return {...state, ...action.payload};
        case REPORT_DETAILS_UPDATE:
                    return {...state, ...action.payload};
        default:
            return state;
    }
}
