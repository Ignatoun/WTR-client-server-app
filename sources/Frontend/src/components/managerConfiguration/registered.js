import React from "react";
import {Link} from "react-router-dom";

export class Registered extends React.Component{
constructor(props){
    super(props)

}

componentDidMount(){
    console.log(this.props)
}

render(){
return(
    <div>
        <table class="table table-inverse shadow">
                          <thead style={{size:"20px"}}>
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
                          <tr/>
                          <tbody>
                                {this.props.reportDetails.allReports.length ? this.props.reportDetails.allReports
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
                                }) : ""}

                              </tbody>
                        </table>
    </div>

)}

}
