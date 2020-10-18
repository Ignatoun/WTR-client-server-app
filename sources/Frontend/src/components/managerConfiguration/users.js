import React from "react";
import {Link} from "react-router-dom";
import {Route,Switch} from "react-router-dom"


export class Users extends React.Component{
constructor(props){
    super(props)

}


onReject(value){
const updateReportDetail ={
    reportDetailsId:value.reportDetailsId,
    status:"REJECTED",
    factor:value.factor,
    location:value.location,
    hours:value.hours,
    workUnits:value.workUnits,
    detailedTask:value.detailedTask,
    task:value.task,
    feature:value.feature,
    project:value.project,
    }
    this.props.updateReportDetail(this.props.reportDetails.allReports,updateReportDetail);
}

componentDidMount(){
    console.log(this.props)
}

render(){
return(
    <div>
        <div style={{paddingRight:"0.5%", paddingLeft: "0.5%"}}>
        <table class="table table-inverse shadow" style={{paddingRight:"10%", paddingLeft: "10%"}}>
                          <thead>
                            <tr>

                              <th>userId</th>
                              <th>userName</th>
                              <th>firstName</th>
                              <th>lastName</th>
                              <th>email</th>
                              <th></th>
                            </tr>
                          </thead>
                          <tbody>
                            {this.props.users.allUsers.length ? this.props.users.allUsers.map(value => {
                                return(
                                    <tr>
                                        <td>{value.userId}</td>
                                        <td>{value.userName}</td>
                                        <td>{value.firstName}</td>
                                        <td>{value.lastName}</td>
                                        <td>{value.email}</td>
                                        <td><Link style={{"color":"black"}} onClick={() => this.props.managerUser(value.userId)}
                                        to={`/update-reportDetail/${value.userId}`}>View</Link></td>
                                    </tr>

                                )
                            }) : ""}
                          </tbody>
                        </table>



    </div>
    </div>

)}
}