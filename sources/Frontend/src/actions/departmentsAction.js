import {DEPARTMENTS_FAILED, DEPARTMENTS_SUCCESS} from "../reducers/departmentsReducer";
import {getAllDepartments} from "../routes/deparmentsRoute";

export function departmentsAction(currentUser) {
    return async dispatch => {
        try {
            const arrayOfDepartments = await getAllDepartments(currentUser.token);
            dispatch({
                type: DEPARTMENTS_SUCCESS,
                payload: {
                    departments: arrayOfDepartments,
                }
            });
        } catch(e) {
            dispatch({
                type: DEPARTMENTS_FAILED,
                payload: {
                    departments: [],
                }
            });
        }
    }
}
