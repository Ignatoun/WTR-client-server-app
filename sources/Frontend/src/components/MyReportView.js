import React from "react";
import {Link, Redirect} from "react-router-dom";
import Menu from "./componentsMyReports/Menu";

class MyReportView extends React.Component{
    state = {
        reportDetail: {},
        redirect: false,
    };

    componentDidMount() {
        fetch(`/user/reportDetails/get/${this.props.params.id}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer_${this.props.currentUser.token}`
            }
        })
            .then(res => res.json())
            .then(data => this.setState({reportDetail: data}));
    }

    onDelete = () => {
        fetch(`/user/reportDetails/${this.props.params.id}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer_${this.props.currentUser.token}`
            }
        })
            .then(res => {
                if (res.status === 200)
                    this.setState({redirect: true});
            })
    };

    render() {
        if(this.state.redirect)
            return <Redirect to={"/search"}/>;
        return (
            <div>
                <Menu currentUser={this.props.currentUser}/>
                <br/>
                {Object.keys(this.state.reportDetail).length === 0 ? "" :
                    <div className="container shadow">
                        <div className="row">
                            <div className="col-12">
                                <br/>
                                <h6>{this.state.reportDetail.status}</h6>
                                <p>{this.state.reportDetail.reportDetailsDate}</p>
                                <h5><strong>Project name: </strong>
                                    {this.state.reportDetail.project.projectName}</h5>
                                <h5><strong>Feature name: </strong>
                                    {this.state.reportDetail.feature.featureName}</h5>
                                <h5><strong>Task name: </strong>
                                    {this.state.reportDetail.task.taskName}</h5>
                                <h5><strong>Detailed task name: </strong>
                                    {this.state.reportDetail.detailedTask.detailedTaskName}</h5>
                                <p>Hours: {this.state.reportDetail.hours},
                                    work units: {this.state.reportDetail.workUnits}</p>
                                <p>Factor: {this.state.reportDetail.factor.factorName},
                                    location: {this.state.reportDetail.location.locationName}</p>
                                <p>Comment: {this.state.reportDetail.comments}</p>
                                <br/>
                                {this.state.reportDetail.status === "PRIVATE" ? (
                                    <button className="btn btn-danger mr-3" onClick={this.onDelete}>
                                        Delete
                                    </button>
                                ) : ""}
                                <button className="btn btn-secondary">
                                    <Link to={"/search"} style={{textDecoration: "none", color: "#fff"}}>
                                        Back
                                    </Link>
                                </button>
                                <br/><br/>
                            </div>
                        </div>
                    </div>
                }
            </div>
        )
    }
}

export default MyReportView