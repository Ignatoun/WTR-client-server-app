import {getLocations} from "../routes/locationsRoute";
import {LOCATIONS_FAILED, LOCATIONS_SUCCESS} from "../reducers/locationsReducer";

export function locationsAction(currentUser) {
    return async dispatch => {
        try {
            const arrayOfLocations = await getLocations(currentUser.token);
            dispatch({
                type: LOCATIONS_SUCCESS,
                payload: {
                    locations: arrayOfLocations,
                    },
                });
        } catch(e) {
            dispatch({
                type: LOCATIONS_FAILED,
                payload: {
                    locations: [],
                }
            });
        }
    }
}
