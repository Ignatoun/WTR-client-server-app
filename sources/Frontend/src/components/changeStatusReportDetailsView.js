import React from "react";
import Menu from "./componentsMyReports/Menu";
import {Link} from "react-router-dom";
import Footer from "./componentsMyReports/Footer";
import Loader from "./Loader";



class ChangeStatusReportDetailsView extends React.Component{
constructor(props){
    super(props)
}

componentDidMount(){

this.props.getReportsByUser(this.props.users.managerUser,this.props.users.currentUser);


console.log(this.props.reportDetails.allReports)
console.log(this.props.users.managerUser)
}


render(){
        return(
            <div>
                <Menu currentUser={this.props.users.currentUser}/>
                <div className="container">
                    <table class="table table-inverse shadow" style={{paddingRight:"10%", paddingLeft: "10%"}}>
                      <thead>
                        <tr>
                          <th>Id</th>
                          <th>Status</th>
                          <th>hours</th>
                          <th>workUnits</th>
                          <th>projectName</th>
                          <th>Feature</th>
                          <th></th>
                        </tr>
                      </thead>
                            <tbody>
                              {this.props.reportDetails.allReports
                              .filter((value) => value.status==="REGISTERED")
                              .map(value => {
                                  return(
                                      <tr >
                                          <td >{value.reportDetailsId}</td>
                                          <td >{value.status}</td>
                                          <td >{value.hours}</td>
                                          <td >{value.workUnits}</td>
                                          <td >{value.project !== null ? value.project.projectName : value.project}</td>
                                          <td >{value.feature !== null ? value.feature.featureName : value.feature}</td>
                                          <td ><Link onClick={() => this.props.getReportDetailId(value.reportDetailsId)}
                                          to={`/update-up/${value.reportDetailsId}`}><span style={{color:"black"}}>View</span></Link></td>
                                      </tr>

                                  )
                              })}

                            </tbody>

                    </table>
                </div>
                <Footer />
            </div>
        )

    }
}

export default ChangeStatusReportDetailsView;