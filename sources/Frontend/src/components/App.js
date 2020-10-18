import React from "react";
import {Route,Switch} from "react-router-dom"
import DailyReport from "../containers/DailyReport";
import WeeklyReport from "./WeeklyReport";
import AdminConfiguration from "../containers/AdminConfiguration";
import AddNewUser from "../containers/AddNewUser";
import UpdateUser from "../containers/UpdateUser";
import Login from "../containers/Login";
import Calendar from "../containers/Calendar";
import ManagerConfiguration from "../containers/ManagerConfiguration";
import ChangeStatusReportDetails from "../containers/ChangeStatusReportDetails"
import Searching from "../containers/Searching";
import ReportDetailByUser from "../containers/reportDetailByUser"
import Scheduler from "../containers/Scheduler";
import MyReport from "../containers/MyReport";

const App = (props) => {
    return (
        <Switch>
            <Route exact path="/" component={Login} />
            <Route path="/MyReports" component={Calendar} />
            <Route path="/daily/:year/:month/:day" component={DailyReport}/>
            <Route path="/weekly/:year/:month/:week" component={WeeklyReport}/>
            <Route path="/configuration" component={AdminConfiguration}/>
            <Route path="/scheduler" component={Scheduler}/>
            <Route path="/new-user" component={AddNewUser}/>
            <Route path="/update-user/:userId" component={UpdateUser}/>
            <Route path="/manager" component={ManagerConfiguration}/>
            <Route path="/search" component={Searching}/>
            <Route path="/manager/users" component={ManagerConfiguration}/>
            <Route path="/update-reportDetail/:userId" component={ChangeStatusReportDetails}/>
            <Route path="/update-up/:reportDetailId" component={ReportDetailByUser}/>
            <Route path="/my-report/:id" component={MyReport}/>
        </Switch>

    )
};

export default App;
