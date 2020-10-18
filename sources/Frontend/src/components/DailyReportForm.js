import React from "react";
import Menu from "./componentsMyReports/Menu";
import Footer from "./componentsMyReports/Footer";
import {getReportsByDate, saveReports, updateReports} from "../routes/reportDetailsRoute";
import {Redirect} from "react-router-dom"
import Loader from "./Loader";
import classNames from "classnames";

class DailyReportForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentKey: 0,
            countOfReports: [0],
            message: "",
            isSubmit: false,
            rejectedReports: [],
            loading: false,
        };
    }

    componentDidMount() {
        this.props.getLocations(this.props.currentUser);
        this.props.getProjects(this.props.currentUser);
        this.props.getFactors(this.props.currentUser);
        const date = [this.props.params.year,
                (+this.props.params.month + 1).toString().length !== 2 ?
                    "0" + (+this.props.params.month + 1) : (+this.props.params.month + 1),
                (+this.props.params.day + 1).toString().length !== 2 ?
                    "0" + (+this.props.params.day) : (+this.props.params.day),].join("-");
        getReportsByDate(date, this.props.currentUser.token)
            .then(res => {
                if (res.status < 400)
                    res.json()
                        .then(data => {
                            console.log(data);
                            const arrOfReports = data.filter((value) => value.status === 'REJECTED' ||
                                value.status === 'PRIVATE').sort((a, b) => a.status > b.status ? 1 : -1);
                            if (arrOfReports.length !== 0)
                                this.setState({
                                    currentKey: arrOfReports.length,
                                    countOfReports: arrOfReports.map((value, index) => index),
                                    rejectedReports: arrOfReports,
                                });
                        });
                });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevState.rejectedReports.length === 0 && this.state.rejectedReports.length !== 0) {
            this.state.rejectedReports.forEach((value, index) => {
                this.fillDocument(value, index, index)
            });
        }
    }

    componentWillUnmount() {
        for(let key in this.props.projects)
            if (key !== "projects")
                this.props.deleteProjects(+key);
    }

    addReport = () => {
        this.setState({
            currentKey: this.state.currentKey + 1,
            countOfReports: [...this.state.countOfReports, this.state.currentKey + 1],
        });
    };

    removeReport = (index) => {
        const updateArr = this.state.rejectedReports;
        updateArr.splice(this.state.countOfReports.indexOf(index), 1);
        this.setState({
            countOfReports: this.state.countOfReports.filter(value => value !== index),
            rejectedReports: updateArr,
        });
    };

    selectProject = (index, reportId) => {
        const projectId = document.getElementsByName("selectProject")[index].value;
        if (projectId) {
            this.props.selectProject(this.props.projects.projects, projectId, reportId);
            this.props.getFeatures(projectId, reportId, this.props.currentUser);
            document.getElementsByName("selectFeature")[index].value = "";
            document.getElementsByName("selectTask")[index].value = "";
            document.getElementsByName("selectDetailedTask")[index].value = "";
        }

    };

    selectFeature = (index, reportId) => {
        const featureId = document.getElementsByName("selectFeature")[index].value;
        if (featureId) {
            this.props.getTasks(featureId, reportId, this.props.currentUser);
            document.getElementsByName("selectTask")[index].value = "";
            document.getElementsByName("selectDetailedTask")[index].value = "";
        }
    };

    selectTask = (index, reportId) => {
        const taskId = document.getElementsByName("selectTask")[index].value;
        if (taskId) {
            this.props.getDetailedTasks(taskId, reportId, this.props.currentUser);
            document.getElementsByName("selectDetailedTask")[index].value = "";
        }
    };

    clickOnSubmit = (status) => {
        try {
            const reports = this.state.countOfReports.map((value, index) => {
                const hours = +document.getElementsByName("inputHours")[index].value;
                const workUnits = +document.getElementsByName("inputWorkUnits")[index].value;
                const detailedTaskId = +document.getElementsByName("selectDetailedTask")[index].value;
                const taskId = +document.getElementsByName("selectTask")[index].value;
                const featureId = +document.getElementsByName("selectFeature")[index].value;
                const projectId = +document.getElementsByName("selectProject")[index].value;
                const comments = document.getElementsByName("comments")[index].value;
                if (projectId === 0)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't choose project in ${index + 1} report`;
                if (featureId === 0)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't choose feature in ${index + 1} report`;
                if (taskId === 0)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't choose task in ${index + 1} report`;
                if (detailedTaskId === 0)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't choose detailed task in ${index + 1} report`;
                if (hours === 0 || hours < 0 || hours > 24)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't input hours in ${index + 1} report`;
                if (workUnits === 0 || workUnits < 0 || workUnits > 24)
                    // eslint-disable-next-line no-throw-literal
                    throw `Didn't input work units in ${index + 1} report`;
                let report = {};
                if (this.state.rejectedReports[index] === undefined ||
                    this.state.rejectedReports[index].status !== "REJECTED")
                    report = {
                        method: "POST",
                        report: {
                            reportDetailsDate: [this.props.params.year,
                                (+this.props.params.month + 1).toString().length !== 2 ?
                                    "0" + (+this.props.params.month + 1) : (+this.props.params.month + 1),
                                (+this.props.params.day).toString().length !== 2 ?
                                    "0" + (+this.props.params.day) : (+this.props.params.day),].join("-"),
                            status: status,
                            factorId: +document.getElementsByName("factors")[index].value,
                            locationId: +document.getElementsByName("locations")[index].value,
                            hours, workUnits, detailedTaskId, taskId, featureId, projectId, comments,
                        }
                    };
                else
                    report = {
                        method: "PUT",
                        report: {
                            reportDetailsId: this.state.rejectedReports[index].reportDetailsId,
                            reportDetailsDate: [this.props.params.year,
                                (+this.props.params.month + 1).toString().length !== 2 ?
                                    "0" + (+this.props.params.month + 1) : (+this.props.params.month + 1),
                                (+this.props.params.day).toString().length !== 2 ?
                                    "0" + (+this.props.params.day) : (+this.props.params.day),].join("-"),
                            status: status,
                            factorId: +document.getElementsByName("factors")[index].value,
                            locationId: +document.getElementsByName("locations")[index].value,
                            hours, workUnits, detailedTaskId, taskId, featureId, projectId, comments,
                        }
                    };
                return report;
            });
            const countOfDays = document.getElementById("countOfDays").value;
            let arrWithReports = [...reports];
            if (countOfDays) {
                for (let i = 1; i <= countOfDays; i++) {
                    let date = new Date(arrWithReports[arrWithReports.length - 1].report.reportDetailsDate);
                    date.setDate(date.getDate() + 1);
                    arrWithReports.push(...reports.map(value => {
                        let report = {...value.report};
                        report.reportDetailsDate = date.toISOString();
                        return {
                            method: "POST",
                            report: report,
                        };
                    }));
                }
            }
            console.log((+this.props.params.day).toString().length !== 2 ?
                "0" + (+this.props.params.day) : (+this.props.params.day));
            console.log(arrWithReports);
            saveReports(arrWithReports
                .filter(report => report.method === "POST")
                .map(report => report.report), this.props.currentUser.token);
            updateReports(arrWithReports
                .filter(report => report.method === "PUT")
                .map(report => report.report), this.props.currentUser.token);
            this.setState({
                isSubmit: true,
            });
        } catch (e) {
            this.setState({
                message: e
            });
        }
    };

    clearData = (reportId, index) => {
        document.getElementsByName("selectProject")[index].value = "";
        document.getElementsByName("selectFeature")[index].value = "";
        document.getElementsByName("selectTask")[index].value = "";
        document.getElementsByName("selectDetailedTask")[index].value = "";
        document.getElementsByName("startDate")[index].value = "";
        document.getElementsByName("endDate")[index].value = "";
        document.getElementsByName("inputHours")[index].value = "";
        document.getElementsByName("inputWorkUnits")[index].value = "";
        document.getElementsByName("comments")[index].value = "";
        document.getElementsByName("factors")[index].value = "1";
        document.getElementsByName("locations")[index].value = "1";
        this.props.deleteProjects(reportId);
        this.props.deleteFeatures(reportId);
        this.props.deleteTasks(reportId);
        this.props.deleteDetailedTasks(reportId);
    };

    copyData = (reportId, index) => {
        const featureId = document.getElementsByName("selectFeature")[index].value;
        const taskId = document.getElementsByName("selectTask")[index].value;
        const detailedTaskId = document.getElementsByName("selectDetailedTask")[index].value;
        const copyDataObject = {
            project: this.props.projects[reportId] || "",
            feature: featureId ? this.props.features[reportId].find(value => value.featureId === +featureId) : {},
            task: taskId ? this.props.tasks[reportId].find(value => value.taskId === +taskId) : {},
            detailedTask: detailedTaskId ? this.props.detailedTasks[reportId].find(value => value.detailedTaskId === +detailedTaskId) : {},
            location: document.getElementsByName("locations")[index].value,
            factor: document.getElementsByName("factors")[index].value,
            hours: document.getElementsByName("inputHours")[index].value,
            workUnits: document.getElementsByName("inputWorkUnits")[index].value,
            comments: document.getElementsByName("comments")[index].value,
        };
        this.props.copyReportDetails(copyDataObject);
    };

    fillDocument = (data, reportId, index) => {
        this.setState({
            loading: true,
        });
        this.props.selectProject(this.props.projects.projects, data.project.projectId, reportId, this.props.currentUser);
        this.props.getFeatures(data.project.projectId, reportId, this.props.currentUser);
        this.props.getTasks(data.feature.featureId, reportId, this.props.currentUser);
        this.props.getDetailedTasks(data.task.taskId, reportId, this.props.currentUser);
        setTimeout(() => {
            document.getElementsByName("selectProject")[index].value = data.project.projectId;
            document.getElementsByName("selectFeature")[index].value = data.feature.featureId;
            document.getElementsByName("selectTask")[index].value = data.task.taskId;
            document.getElementsByName("selectDetailedTask")[index].value = data.detailedTask.detailedTaskId;
            document.getElementsByName("startDate")[index].value = data.project.startDate;
            document.getElementsByName("endDate")[index].value = data.project.endDate;
            document.getElementsByName("inputHours")[index].value = data.hours;
            document.getElementsByName("inputWorkUnits")[index].value = data.workUnits;
            document.getElementsByName("comments")[index].value = data.comments;
            document.getElementsByName("factors")[index].value = data.factor.factorId || data.factor;
            document.getElementsByName("locations")[index].value = data.location.locationId || data.location;
            this.setState({
                loading: false,
            });
        }, 500);
    };


    insertData = (reportId, index) => {
        if (Object.keys(this.props.reportDetails.copy).length !== 0) {
            const reportDetails = this.props.reportDetails.copy;
            this.fillDocument(reportDetails, reportId, index);
        }
    };

    render() {
        const MONTH = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        const date = new Date([this.props.params.year, +this.props.params.month + 1, this.props.params.day].join("-"));
        if (this.state.isSubmit)
            return <Redirect to="/MyReports" />;
        return (
            <div>
                <Menu currentUser={this.props.currentUser}/>
                <div style={{display: this.state.loading ? "inline" : "none"}}>
                    <Loader/>
                </div>
                {this.state.countOfReports.map((value, index) => {
                    return (
                        <div className="container" key={value}>
                            <div className="row">
                                <div className="col-12 shadow">
                                    <br/>
                                    <div className="row">
                                        <div className="col-6">
                                            <h2>
                                                {
                                                    [MONTH[this.props.params.month], this.props.params.day, this.props.params.year].join(" - ")
                                                }
                                            </h2>
                                            {this.state.rejectedReports[index] === undefined ? console.log(this.state.rejectedReports) : (
                                                <h6 style={{color: this.state.rejectedReports[index].status === "PRIVATE" ?
                                                    "lightgray" : "red"}}>
                                                {this.state.rejectedReports[index].status}
                                            </h6>
                                            )}
                                        </div>
                                        <div className="offset-1"/>
                                        {this.state.countOfReports.length - 1 === index ?
                                            (
                                                <div className="col-2">
                                                    <button
                                                        onClick={this.addReport.bind(this, value)}
                                                        className="btn btn-success">
                                                        Add new report
                                                    </button>
                                                </div>): ""
                                        }
                                        {this.state.countOfReports.length > 1 ?
                                            (
                                                <div className="col-2">
                                                    <button
                                                        onClick={this.removeReport.bind(this, value)}
                                                        className="btn btn-danger">
                                                        Remove report
                                                    </button>
                                                </div>): ""
                                        }

                                    </div>
                                    <form>
                                        <div className="form-row">
                                            <div className="col mb-3">
                                                <label>Project</label>
                                                <select
                                                    className="custom-select" id="validationServer04"
                                                    name="selectProject" required
                                                    onChange={this.selectProject.bind(this, index, value)}
                                                    defaultValue={this.props.projects[value] || ""}
                                                >
                                                    <option disabled value="">Choose...</option>
                                                    {
                                                        this.props.projects.projects.map(project => {
                                                            return <option key={project.projectId} value={project.projectId}>
                                                                {project.projectName}
                                                            </option>
                                                        })
                                                    }
                                                </select>
                                            </div>
                                            <div className="col mb-3">
                                                <label htmlFor="inputStartDate">Start Date</label>
                                                <input type="text" className="form-control" id="inputStartDate" name="startDate"
                                                    defaultValue={
                                                        this.props.projects[value]
                                                        ? this.props.projects[value].startDate : ""
                                                    }
                                                />
                                            </div>
                                            <div className="col mb-3">
                                                <label htmlFor="inputEndDate">End Date</label>
                                                <input type="text" className="form-control" id="inputEndDate" name="endDate"
                                                       defaultValue={
                                                           this.props.projects[value]
                                                               ? this.props.projects[value].endDate : ""
                                                       }
                                                />
                                            </div>
                                        </div>
                                        <div className="form-row">
                                            <div className="col mb-3">
                                                <label>Feature</label>
                                                <select
                                                    defaultValue=""
                                                    className="custom-select" id="validationServer04"
                                                    name="selectFeature" required
                                                    onChange={this.selectFeature.bind(this, index, value)}>
                                                    <option disabled value="">Choose...</option>
                                                    {
                                                        this.props.features[value] ?
                                                            this.props.features[value].map(feature => {
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
                                                        name="selectTask"
                                                        defaultValue="" className="custom-select" id="validationServer04"
                                                        required onChange={this.selectTask.bind(this, index, value)}>
                                                        <option disabled value="">Choose...</option>
                                                        {
                                                            this.props.tasks[value] ?
                                                                this.props.tasks[value].map(task => {
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
                                                <select name="selectDetailedTask" defaultValue=""
                                                        className="custom-select" id="validationServer04" required>
                                                    <option disabled value="" >Choose...</option>
                                                    {
                                                        this.props.detailedTasks[value] ?
                                                            this.props.detailedTasks[value].map(detailedTask => {
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
                                            <div className="form-group col-md-6 font-weight-bold">
                                                <label htmlFor="inputHours">Hours</label>
                                                <input type="text" className="form-control" name="inputHours"
                                                    defaultValue={date.getDay() === 0 || date.getDay() === 6 ?
                                                    0 : 8}/>
                                            </div>
                                            <div className="form-group col-md-6 font-weight-bold">
                                                <label htmlFor="inputWorkUnits">Work Units</label>
                                                <input type="text" className="form-control" name="inputWorkUnits" defaultValue={0}/>
                                            </div>
                                        </div>
                                        <div className="form-row">
                                            <div className="col mb-3">
                                                <label>Factor</label>
                                                <select className="custom-select" name="factors"
                                                        defaultValue={date.getDay() === 0 || date.getDay() === 6 ?
                                                    "8" : "1"}>
                                                    {
                                                    this.props.factors.map((value) => (
                                                    <option key={value.factorId} value={value.factorId}>
                                                        {value.factorName}
                                                    </option>
                                                    ))
                                                    }
                                                </select>
                                            </div>
                                            <div className="col mb-3">
                                                <label>Location</label>
                                                <select className="custom-select"
                                                        name="locations" required defaultValue="1">
                                                    {
                                                        this.props.locations.map((value) => {
                                                        return <option key={value.locationId}
                                                        value={value.locationId}>{value.locationName}</option>
                                                    })
                                                    }
                                                </select>
                                            </div>
                                        </div>
                                        <div className="form-row">
                                            <div className="col mb-3">
                                                <label>Comment</label>
                                                <textarea name="comments" className="form-control" aria-label="With textarea" placeholder="Your comment..."/>
                                            </div>
                                        </div>
                                        <button type="button" className="btn btn-danger mb-3 mr-3"
                                                onClick={this.clearData.bind(this, value, index)}>
                                            Clear
                                        </button>
                                        <button type="button" className="btn btn-warning mb-3 mr-3"
                                            onClick={this.copyData.bind(this, value, index)}
                                        >Copy</button>
                                        <button type="button" className={
                                            classNames("btn btn-warning mb-3 mr-3", {
                                                "disabled": Object.keys(this.props.reportDetails.copy).length === 0,
                                            })}
                                                onClick={this.insertData.bind(this, value, index)}
                                        >Insert</button>
                                    </form>
                                </div>
                            </div>
                            <br/>
                        </div>
                    )
                })}
                <div className="container">
                    <p style={{color: "red"}}>{this.state.message}</p>
                    <br/>
                    <h5>Copy report on the
                        {" "}
                        <select id="countOfDays" defaultValue="">
                            <option value=""/>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>

                        </select>
                        {" "}
                        next days</h5>
                    <br/>
                    <button type="button" className="btn btn-primary btn-lg mb-3 mr-3"
                            onClick={this.clickOnSubmit.bind(this, "REGISTERED")}>
                        Submit
                    </button>
                    <button type="button" className="btn btn-dark btn-lg mb-3 mr-3"
                            onClick={this.clickOnSubmit.bind(this, "PRIVATE")}>
                        Submit as Private
                    </button>
                    <button type="button" className="btn btn-secondary btn-lg mb-3 mr-3">Cancel</button>
                </div>
                <Footer/>
            </div>
        )
    }
}

export default DailyReportForm;
