import {
    REPORT_DETAILS_COPY,
    REPORT_DETAILS_FAILED,
    REPORT_DETAILS_REQUEST,
    REPORT_DETAILS_SUCCESS,
    REPORT_DETAILS_UPDATE

} from "../reducers/reportDetailsReducer";
import {getReportDetailsForMonth,allReportDetailsManager,getAllReportDetails,
updateReportDetailById} from "../routes/reportDetailsRoute";


export function putNewReportDetails(reports) {
    return async dispatch => {
        dispatch({
            type: REPORT_DETAILS_REQUEST,
            payload: {
                allReports:null
            }
        });
        try {

            dispatch({
                type: REPORT_DETAILS_SUCCESS,
                payload: {
                    allReports:reports
                }
            });
        } catch(e) {
            dispatch({
                type: REPORT_DETAILS_FAILED,
                payload: {
                    allReports:null
                }
            });
        }
    }

}

export function reportDetailsAction(date, currentUser) {
    return async dispatch => {
        dispatch({
            type: REPORT_DETAILS_REQUEST,
            payload: {
                request: true,
                currentMonth: [],
            }
        });
        try {
            const arrayOfReports = await getReportDetailsForMonth(
                date.getMonth() + 1, date.getFullYear(), currentUser.token
            );
            dispatch({
                type: REPORT_DETAILS_SUCCESS,
                payload: {
                    request: false,
                    currentMonth: arrayOfReports.map(day => {
                        return new Date(date.getFullYear(), date.getMonth(), day).valueOf()
                    }),
                }
            });
        } catch(e) {
            dispatch({
                type: REPORT_DETAILS_FAILED,
                payload: {
                    request: false,
                    currentMonth: [],
                }
            });
        }
    }
}

export function reportDetailsCopy(report) {
    return {
        type: REPORT_DETAILS_COPY,
        payload: {
            copy: report,
        }
    }
}

export function getReportDetailId(reportId) {
    return async dispatch => {
        dispatch({
            type: REPORT_DETAILS_REQUEST,
            payload: {
                detailsId: null,
            }
        });
        try {

            dispatch({
                type: REPORT_DETAILS_SUCCESS,
                payload: {
                    detailsId: reportId,
                }
            });
        } catch(e) {
            dispatch({
                type: REPORT_DETAILS_FAILED,
                payload: {
                    detailsId:null,
                }
            });
        }
    }

}

export function allReportDetails(userId,currentUser) {
    return async dispatch => {
        dispatch({
            type: REPORT_DETAILS_REQUEST,
            payload: {
                allReports: [],
                loading: true,
            }
        });
        try {
            const arrayOfReportDetails = await allReportDetailsManager(userId,currentUser.token);
            dispatch({
                type: REPORT_DETAILS_SUCCESS,
                payload: {
                    allReports: arrayOfReportDetails,
                    loading: false,
                }
            });
        } catch(e) {
            dispatch({
                type: REPORT_DETAILS_FAILED,
                payload: {
                    allReportDetails: [],
                    loading: false,
                }
            });
        }
    }
}

export function getAllReportDetailsRejected(currentUser) {
    return async dispatch => {
        dispatch({
            type: REPORT_DETAILS_REQUEST,
            payload: {
                allReports: [],
                loading: true,
            }
        });
        try {
            const arrayOfReportDetails = await getAllReportDetails(currentUser.token);
            dispatch({
                type: REPORT_DETAILS_SUCCESS,
                payload: {
                    allReports: arrayOfReportDetails,
                    loading: false,
                }
            });
        } catch(e) {
            dispatch({
                type: REPORT_DETAILS_FAILED,
                payload: {
                    allReportDetails: [],
                    loading: false,
                }
            });
        }
    }
}

export function updateReportDetail(reportDetails, reportDetail,token,getReportDetail) {
    return dispatch => {
        updateReportDetailById(reportDetail,token)
            .then(res => {
                if (res.reportDetailsId)
                    dispatch({
                        type: REPORT_DETAILS_UPDATE,
                        payload: {
                            allReports: reportDetails.map(oldReport => oldReport.reportDetailsId === reportDetail.reportDetailsId ? getReportDetail : oldReport),
                        }
                    });
                else
                    dispatch({
                        type: REPORT_DETAILS_FAILED,
                        payload: {
                            allReports: reportDetails
                        }
                    });
            })
    }
}

