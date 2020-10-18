import React from "react";
import Menu from "./componentsMyReports/Menu";
import Footer from "./componentsMyReports/Footer";
import Loader from "./Loader";
import {Users} from "./managerConfiguration/users"
import {Registered} from "./managerConfiguration/registered"
import {Route,Switch} from "react-router-dom"
import {SearchView} from "./managerConfiguration/search"

class ManagerConfigurationView extends React.Component {
     constructor(props){
        super(props)
     }
     componentDidMount() {
            console.log(this.props);
            this.props.getAllReportDetailsRejected(this.props.users.currentUser)
            this.props.getAllUsersAction(this.props.users.currentUser);


     }



render(){
    return(
        <div>
            <Menu currentUser={this.props.users.currentUser}/>
            {this.props.reportDetails.loading ? <Loader /> : ""}
            <div className="container">
                <nav class="navbar navbar-expand-lg navbar-light bg-light" >
                    <div class="collapse navbar-collapse" id="navbarNav" >
                        <ul class="nav nav-tabs">

                            <li class="nav-item">
                              <a class="nav-link" href="/manager/registered">Registered</a>
                            </li>

                            <li class="nav-item">
                              <a class="nav-link" href="/manager/users">Users</a>
                            </li>

                        </ul>
                        <ul class="nav nav-tabs" style={{"margin-left":"74.50%"}}>
                            <div style={{float: "right"}}>
                                <li class="nav-item">
                                  <a class="nav-link" href="/manager/search">Search</a>
                                </li>

                            </div>
                            </ul>
                    </div>
                </nav>
                <Switch>
                    <Route exact path="/manager/registered" render={(props) => (<Registered users={this.props.users}
                    reportDetails={this.props.reportDetails} getReportDetailId={this.props.getReportDetailId}/>)}/>
                    <Route path="/manager/users" render={(props) => (<Users users={this.props.users}
                        managerUser={this.props.managerUser}/>)}/>
                    <Route path="/manager/search" render={() => (<SearchView
                        getFactors={this.props.getFactors}
                        getLocations={this.props.getLocations}
                        getProjects={this.props.getProjects}
                        getFeatures={this.props.getFeatures}
                        getTasks={this.props.getTasks}
                        getDetailedTasks={this.props.getDetailedTasks}
                        projects={this.props.projects}
                        features={this.props.features}
                        tasks={this.props.tasks}
                        detailedTasks={this.props.detailedTasks}
                        locations={this.props.locations}
                        factors={this.props.factors}
                        currentUser={this.props.currentUser}
                        putNewReportDetails={this.props.putNewReportDetails}
                        getReportDetailId={this.props.getReportDetailId}
                    />)}/>



                </Switch>
            </div>
            <Footer />
        </div>
    )}
}

export default ManagerConfigurationView;
