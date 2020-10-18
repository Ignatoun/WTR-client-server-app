import React from "react";

export class Rejected extends React.Component{
constructor(props){
    super(props)

}

componentDidMount(){
    console.log(this.props)
}

render(){
return(
    <div>
        <table class="table table-inverse">
                          <thead>
                            <tr>
                              <th>Id</th>
                              <th>date</th>
                              <th>status</th>
                              <th>factor</th>
                              <th>location</th>
                              <th>hours</th>
                              <th>workUnits</th>
                              <th>detailedTask</th>
                              <th>task</th>
                              <th>feature</th>
                              <th>project</th>
                              <th></th>
                              <th></th>
                            </tr>
                          </thead>
                          <tbody>
                            {this.props.reportDetails.allReports
                            .filter((value) => value.status==="REJECTED")
                            .map(value => {
                                return(
                                    <tr>
                                        <th scope="row">{value.reportDetailsId}</th>
                                        <td>{value.reportDetailsDate}</td>
                                        <td>{value.status}</td>
                                        <td>{value.factor.factorName}</td>
                                        <td>{value.location.locationName}</td>
                                        <td>{value.hours}</td>
                                        <td>{value.workUnits}</td>
                                        <td>{value.detailedTask !== null ? value.detailedTask.detailedTaskName : value.detailedTask}</td>
                                        <td>{value.task !== null ? value.task.taskName : value.task}</td>
                                        <td>{value.feature !== null ? value.feature.featureName : value.feature}</td>
                                        <td>{value.project !== null ? value.project.projectName : value.project}</td>
                                        <td><button/></td>
                                        <td><button/></td>
                                    </tr>
                                )
                            })}
                          </tbody>
                        </table>
    </div>

)}

}
