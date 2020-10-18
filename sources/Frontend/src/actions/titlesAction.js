import {getAllTitles} from "../routes/titleRoute";
import {TITLES_FAILED, TITLES_SUCCESS} from "../reducers/titlesReducer";

export function titlesAction(currentUser) {
    return async dispatch => {
        try {
            const arrayOfTitles = await getAllTitles(currentUser.token);
            dispatch({
                type: TITLES_SUCCESS,
                payload: {
                    titles: arrayOfTitles,
                }
            });
        } catch(e) {
            dispatch({
                type: TITLES_FAILED,
                payload: {
                    titles: [],
                }
            });
        }
    }
}
