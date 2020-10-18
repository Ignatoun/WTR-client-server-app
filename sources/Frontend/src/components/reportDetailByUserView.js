import React from "react";
import Menu from "./componentsMyReports/Menu";
import Footer from "./componentsMyReports/Footer";
import Loader from "./Loader";
import {Users} from "./managerConfiguration/users"
import {Link, Route, Switch} from "react-router-dom"
import {Redirect} from "react-router-dom"

class ReportDetailByUserView extends React.Component {
     constructor(props){
        super(props)
        this.state={
            per:false
        }
     }
     componentDidMount() {
        this.props.getReportsByUser(this.props.users.managerUser,this.props.users.currentUser);
     }

     onReject(value){
     const getReportDetail = {
         reportDetailsId:value.reportDetailsId,
         status:"REJECTED",
         factor:value.factor,
         location:value.location,
         hours:value.hours,
         workUnits:value.workUnits,
         detailedTask:null,
         task:null,
         feature:null,
         project:null,
     }
     const updateReportDetail = {
         reportDetailsId:value.reportDetailsId,
         status:"REJECTED",
         reportDetailsDate:value.reportDetailsDate,
         factorId:value.factor.factorId,
         locationId:value.location.locationId,
         hours:value.hours,
         workUnits:value.workUnits,
         detailedTaskId:value.detailedTask.detailedTaskId,
         taskId:value.task.taskId,
         featureId:value.feature.featureId,
         projectId:value.project.projectId,
         projectName:value.project.projectName,
         comments:"value.comments",

         }
         this.props.updateReportDetail(this.props.reportDetails.allReports,updateReportDetail,this.props.users.currentUser.token,getReportDetail);

         this.setState({
             per:true
         })
     }
        onApproved(value){
          const getReportDetail = {
              reportDetailsId:value.reportDetailsId,
              status:"APPROVED",
              factor:value.factor,
              location:value.location,
              hours:value.hours,
              workUnits:value.workUnits,
              detailedTask:null,
              task:null,
              feature:null,
              project:null,
          }
          const updateReportDetail = {
              reportDetailsId:value.reportDetailsId,
              status:"APPROVED",
              reportDetailsDate:value.reportDetailsDate,
              factorId:value.factor.factorId,
              locationId:value.location.locationId,
              hours:value.hours,
              workUnits:value.workUnits,
              detailedTaskId:value.detailedTask.detailedTaskId,
              taskId:value.task.taskId,
              featureId:value.feature.featureId,
              projectId:value.project.projectId,
              projectName:value.project.projectName,
              comments:"value.comments",

              }
              this.props.updateReportDetail(this.props.reportDetails.allReports,updateReportDetail,this.props.users.currentUser.token,getReportDetail);

              this.setState({
                  per:true
              })
          }

    render(){
        if(this.state.per === true)
            return <Redirect to="/manager/users" />;
        return(
            <div>
                <Menu currentUser={this.props.users.currentUser}/>

                {this.props.reportDetails.allReports
                .filter((value) => value.reportDetailsId === this.props.reportDetails.detailsId)
                .map((value) => {return(
                <div className="container">
                    <div className="container">
                        <div className="font-weight-bold">ReportDetailsId: {this.props.reportDetails.detailsId}</div>
                        <div className="row">
                            <div className="col-12 shadow">
                                <br/>

                                <form>
                                    <div className="form-row">
                                    <div className="col mb-3">
                                        <label className="font-weight-bold" style={{color:"red"}}>Status</label><br/>
                                        {value.status}
                                    </div>
                                    <div className="col mb-3">
                                        <label className="font-weight-bold" style={{color:"blue"}}>ReportDetailsDate</label><br/>
                                        {value.reportDetailsDate}
                                    </div>
                                    </div>
                                    <br/>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Factor</label><br/>
                                            {value.factor !== null ? value.factor.factorName : value.factor}
                                        </div>

                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Location</label><br/>
                                            {value.location !== null ? value.location.locationName : value.location}
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Project</label><br/>
                                            {value.project !== null ? value.project.projectName : value.project}
                                        </div>
                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Task</label><br/>
                                            {value.task !== null ? value.task.taskName : value.task}
                                        </div>

                                    </div>
                                    <div className="form-row">

                                        <div className="col mb-3">
                                            <label className="font-weight-bold">DetailedTask</label><br/>
                                            {value.detailedTask !== null ? value.detailedTask.detailedTaskName : value.detailedTask}
                                        </div>

                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Feature</label><br/>
                                            {value.feature !== null ? value.feature.featureName : value.feature}
                                        </div>
                                    </div>

                                    <div className="form-row">
                                        <div className="form-group col-md-6 ">
                                            <label className="font-weight-bold">Hours</label><br/>
                                            {value.hours}
                                        </div>
                                        <div className="form-group col-md-6 ">
                                            <label className="font-weight-bold">Work Units</label><br/>
                                            {value.workUnits}
                                        </div>
                                    </div>

                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label className="font-weight-bold">Comment</label><br/>
                                            {value.comments}
                                        </div>
                                    </div>
                                </form>


                            </div>
                        </div>
                        <br/>
                    </div>
                    <div className="container">
                        <button onClick={() => this.onApproved(value)}
                        type="button" className="btn btn-primary btn-lg mb-3 mr-3">Approved</button>
                        <button onClick={() => this.onReject(value)}
                        type="button" className="btn btn-dark btn-lg mb-3 mr-3">Reject</button>
                        <button type="button" className="btn btn-secondary btn-lg mb-3 mr-3">
                            <Link to={"/manager/registered"}>Back</Link>
                        </button>
                    </div>
                </div>
                    )
                })}
                <Footer/>
            </div>

        )
    }
}

export default ReportDetailByUserView;
