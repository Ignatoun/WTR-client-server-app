import React from "react"
import Menu from "../componentsMyReports/Menu";
import {searchReportDetailManager} from "../../routes/reportDetailsRoute";
import {Link} from "react-router-dom";

export class SearchView extends React.Component {
    state = {
        arr: [],
    };

    componentDidMount() {
        this.props.getProjects(this.props.currentUser);
        this.props.getFeatures(this.props.currentUser);
        this.props.getTasks(this.props.currentUser);
        this.props.getDetailedTasks(this.props.currentUser);
        this.props.getFactors(this.props.currentUser);
        this.props.getLocations(this.props.currentUser);
    }

    onSubmit = event => {
        event.preventDefault();
        const report = {
            projectId: document.getElementById("selectProject").value || "",
            featureId: document.getElementById("selectFeature").value || "",
            taskId: document.getElementById("selectTask").value || "",
            detailedTaskId: document.getElementById("selectTask").value || "",
            factorId: document.getElementById("factors").value || "",
            locationId: document.getElementById("locations").value || "",
            dateStart: document.getElementById("inputStartDate").value || "",
            dateEnd: document.getElementById("inputEndDate").value || "",
            status: document.getElementById("status").value || "",
        };
        searchReportDetailManager(report, this.props.currentUser.token)
            .then(res => res.json())
            .then(data => this.setState({arr:data}));
        console.log(this.state.arr)
    };

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="col-6 shadow">
                            <br/>
                            <h3>Searching</h3>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Project</label>
                                        <select
                                            className="custom-select"
                                            id="selectProject" defaultValue="">
                                            <option value="">Choose...</option>
                                            {
                                                this.props.projects ? this.props.projects.map(project => {
                                                    return <option key={project.projectId} value={project.projectId}>
                                                        {project.projectName}
                                                    </option>
                                                }) : ""
                                            }
                                        </select>
                                    </div>
                                    <div className="col mb-3">
                                        <label htmlFor="inputStartDate">Start Date</label>
                                        <input type="text" className="form-control" id="inputStartDate" name="startDate"/>
                                    </div>
                                    <div className="col mb-3">
                                        <label htmlFor="inputEndDate">End Date</label>
                                        <input type="text" className="form-control" id="inputEndDate" name="endDate"/>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Feature</label>
                                        <select
                                            defaultValue=""
                                            className="custom-select" id="validationServer04"
                                            id="selectFeature">
                                            <option value="">Choose...</option>
                                            {
                                                this.props.features ? this.props.features.map(feature => {
                                                    return <option key={feature.featureId} value={feature.featureId}>
                                                        {feature.featureName}
                                                    </option>
                                                }) : ""
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Task</label>
                                        <select
                                            id="selectTask"
                                            defaultValue="" className="custom-select">
                                            <option value="">Choose...</option>
                                            {
                                                this.props.tasks ? this.props.tasks.map(task => {
                                                    return <option key={task.taskId} value={task.taskId}>
                                                        {task.taskName}
                                                    </option>
                                                }) : ""
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Detailed Task</label>
                                        <select id="selectDetailedTask" defaultValue=""
                                                className="custom-select" >
                                            <option value="" >Choose...</option>
                                            {
                                                this.props.detailedTasks ? this.props.detailedTasks.map(detailedTask => {
                                                    return <option key={detailedTask.detailedTaskId}
                                                                   value={detailedTask.detailedTaskId}>
                                                        {detailedTask.detailedTaskName}
                                                    </option>
                                                }) : ""
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Factor</label>
                                        <select className="custom-select" id="factors" defaultValue="">
                                            <option value="">Choose...</option>
                                            {
                                                this.props.factors ? this.props.factors.map((value) => (
                                                    <option key={value.factorId} value={value.factorId}>
                                                        {value.factorName}
                                                    </option>
                                                )) : ""
                                            }
                                        </select>
                                    </div>
                                    <div className="col mb-3">
                                        <label>Location</label>
                                        <select className="custom-select"
                                                id="locations" defaultValue="">
                                            <option value="">Choose...</option>
                                            {
                                                this.props.locations ? this.props.locations.map((value) => {
                                                    return <option key={value.locationId}
                                                                   value={value.locationId}>{value.locationName}</option>
                                                }) : ""
                                            }
                                        </select>
                                    </div>
                                    <div className="col mb-3">
                                        <label>Status</label>
                                        <select className="custom-select"
                                                id="status" defaultValue="0">
                                            <option value="">Choose...</option>
                                            <option value="PRIVATE">Private</option>
                                            <option value="REJECTED">Rejected</option>
                                            <option value="APPROVED">Approved</option>
                                            <option value="REGISTERED">Registered</option>
                                        </select>
                                    </div>
                                </div>
                                <button type="submit" className="btn btn-success mb-3 mr-3">Search</button>
                            </form>
                        </div>
                        <div className="col-6 shadow overflow-auto" style={{height: 550}}>
                            <ul className="list-group">
                                {this.state.arr.map(report => {
                                    console.log(report);
                                    return <li key={report.reportDetailsId} className="list-group-item">
                                        <p><small>Date: {report.reportDetailsDate}</small></p>
                                        <p><small>{report.project ? report.project.projectName : ""}</small></p>
                                        <p>
                                            <small>Hours: {report.hours}</small> <small>Work units: {report.workUnits}</small>

                                            <Link onClick={() => this.props.getReportDetailId(report.reportDetailsId)}
                                            to={`/update-up/${report.reportDetailsId}`}>
                                            <span style={{color:"black","margin-left":"50%"}}>View</span>
                                            </Link>
                                        </p>
                                    </li>
                                })}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
