import {
    USERS_ADD, USERS_AUTH,
    USERS_DELETE,
    USERS_FAILED,
    USERS_REQUEST,
    USERS_SUCCESS,
    USERS_UPDATE
} from "../reducers/usersReducer";
import {addNewUser, authorization, deleteUserById, getAllUsers, updateUserById,getAllUsersManager} from "../routes/usersRoutes";

export function managerUser(userId){
    return async dispatch => {
        dispatch({
            type:USERS_REQUEST,
            payload:{
                managerUser:null,
            }
        });

        try {

                    dispatch({
                        type: USERS_SUCCESS,
                        payload: {
                            managerUser: userId,
                        }
                    });
                } catch(e) {
                    dispatch({
                        type: USERS_FAILED,
                        payload: {
                            managerUser: null,
                        }
                    });
                }

    }
}

export function getAllUsersAction(currentUser){
    return async dispatch => {
        dispatch({
            type:USERS_REQUEST,
            payload:{
                allUsers:[],
                loading:true
            }
        });

        try {
                    const arrayOfUsers = await getAllUsersManager(currentUser.token);
                    dispatch({
                        type: USERS_SUCCESS,
                        payload: {
                            allUsers: arrayOfUsers,
                            loading: false,
                        }
                    });
                } catch(e) {
                    dispatch({
                        type: USERS_FAILED,
                        payload: {
                            allUsers: [],
                            loading: false,
                        }
                    });
                }

    }
}

export function usersAction(currentUser) {
    return async dispatch => {
        dispatch({
            type: USERS_REQUEST,
            payload: {
                allUsers: [],
                loading: true,
            }
        });
        try {
            const arrayOfUsers = await getAllUsers(currentUser.token);
            dispatch({
                type: USERS_SUCCESS,
                payload: {
                    allUsers: arrayOfUsers,
                    loading: false,
                }
            });
        } catch(e) {
            dispatch({
                type: USERS_FAILED,
                payload: {
                    allUsers: [],
                    loading: false,
                }
            });
        }
    }
}

export function addUser(users, user, currentUser) {
    return dispatch => {
        console.log(currentUser);
        addNewUser(user, currentUser.token)
            .then(data => {
                let updateArr = users;
                users.push(user);
                dispatch({
                    type: USERS_ADD,
                    payload: {
                        users: updateArr,
                    }
                })
            })
    }
}

export function deleteUser(users, userId, currentUser) {
    return dispatch => {
        deleteUserById(userId, currentUser.token)
            .then(res => {
                if (res.status === 200)
                    dispatch({
                        type: USERS_DELETE,
                        payload: {
                            allUsers: users.filter(user => user.userId !== userId),
                        }
                    });
                else
                    dispatch({
                        type: USERS_DELETE,
                        payload: {
                            allUsers: users
                        }
                    });
            })
    }
}

export function updateUser(users, user, currentUser) {
    return dispatch => {
        updateUserById(user, currentUser.token)
            .then(res => {
                console.log(res);
                if (res.userId)
                    dispatch({
                        type: USERS_UPDATE,
                        payload: {
                            allUsers: users.map(oldUser => oldUser.userId === user.userId ? user : oldUser),
                        }
                    });
                else
                    dispatch({
                        type: USERS_FAILED,
                        payload: {
                            allUsers: users
                        }
                    });
            })
            .catch(e => console.log(e));
    }
}

export function userAuthorization(user) {
    return async dispatch => {
        dispatch({
            type: USERS_REQUEST,
            payload: {
                currentUser: {},
                message: "",
                loading: true,
            }
        });
        try {
            const result = await authorization(user);
            if (result.token !== undefined)
                dispatch({
                    type: USERS_AUTH,
                    payload: {
                        currentUser: result,
                        message: "",
                        loading: false,
                    },
                });
            else
                dispatch({
                    type: USERS_FAILED,
                    payload: {
                        currentUser: {},
                        message: result.debugMessage,
                        loading: false,
                    },
                })
        } catch (e) {
            dispatch({
                type: USERS_FAILED,
                payload: {
                    currentUser: {},
                    message: "Something is wrong:(",
                    loading: false,
                },
            })
        }
    }
}
