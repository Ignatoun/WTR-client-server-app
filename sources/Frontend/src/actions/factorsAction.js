import {getFactors} from "../routes/factorRoute";
import {FACTORS_FAILED, FACTORS_SUCCESS} from "../reducers/factorsReducer";

export function factorsAction(currentUser) {
    return async dispatch => {
        try {
            const arrayOfFactors = await getFactors(currentUser.token);
            dispatch({
                type: FACTORS_SUCCESS,
                payload: {
                    factors: arrayOfFactors,
                    },
                });
        } catch(e) {
            dispatch({
                type: FACTORS_FAILED,
                payload: {
                    factors: [],
                }
            });
        }
    }
}
