import {combineReducers} from "redux";
import {reportDetailsReducer} from "./reportDetailsReducer";
import {locationsReducer} from "./locationsReducer";
import {projectsReducer} from "./projectsReducer";
import {factorsReducer} from "./factorsReducer"
import {featuresReducer} from "./featuresReducer";
import {tasksReducer} from "./tasksReducer";
import {detailedTasksReducer} from "./detailedTasksReducer";
import {usersReducer} from "./usersReducer";
import {departmentsReducer} from "./departmentsReducer";
import {titlesReducer} from "./titlesReducer";


export const rootReducer = combineReducers({
    reportDetails: reportDetailsReducer,
    locations: locationsReducer,
    projects: projectsReducer,
    factors: factorsReducer,
    features: featuresReducer,
    tasks: tasksReducer,
    detailedTasks: detailedTasksReducer,
    users: usersReducer,
    departments: departmentsReducer,
    titles: titlesReducer,
});
