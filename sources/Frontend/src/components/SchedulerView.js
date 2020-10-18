import React from "react";
import Menu from "./componentsMyReports/Menu";
import {Redirect} from "react-router-dom"

class SchedulerView extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            message: "",
            scheduler: [],
            isSubmit: false,
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevState.scheduler.length === 0 && this.state.scheduler.length !== 0) {
            document.getElementById("days").value = this.state.scheduler[0].value;
            document.getElementById("hours").value = this.state.scheduler[1].value;
            document.getElementById("minutes").value = this.state.scheduler[2].value;
            document.getElementById("seconds").value = this.state.scheduler[3].value;
        }
    }

    componentDidMount() {
        fetch("/admin/scheduler", {
            method: "GET",
            headers: {
                "Authorization": `Bearer_${this.props.currentUser.token}`
            }
        })
            .then(res => res.json())
            .then(data => this.setState({scheduler: data}));
    }

    onSubmit = event => {
        event.preventDefault();
        this.setState({message: ""});
        try {
            const days = document.getElementById("days").value;
            const hours = +document.getElementById("hours").value;
            if (hours < 0 || hours > 23)
                throw "Error in hours!";
            const minutes = document.getElementById("minutes").value;
            if (minutes < 0 || minutes > 59)
                throw "Error in minutes!";
            const seconds = document.getElementById("seconds").value;
            if (seconds < 0 || seconds > 59)
                throw "Error in seconds!";
            const updateArr = [...this.state.scheduler];
            updateArr[0].value = days;
            updateArr[1].value = hours;
            updateArr[2].value = minutes;
            updateArr[3].value = seconds;
            fetch("/admin/scheduler/list", {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer_${this.props.currentUser.token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updateArr),
            })
                .then(res => res.json())
                .then(data => this.setState({isSubmit: true}));
        } catch (e) {
            this.setState({message: e});
        }
    };

    render() {
        if(this.state.isSubmit)
            return <Redirect to={"/MyReports"}/>;
        return (
            <div>
                <Menu currentUser={this.props.currentUser}/><br/>
                <div className="container shadow">
                    <br/>
                    <h3>Scheduler</h3>
                    <form onSubmit={this.onSubmit}>
                        <div className="form-row">
                            <div className="col mb-3">
                                <label>Day</label>
                                <select className="custom-select" id="days" required>
                                    <option value="MON">MON</option>
                                    <option value="TUE">TUE</option>
                                    <option value="WED">WED</option>
                                    <option value="THU">THU</option>
                                    <option value="FRI">FRI</option>
                                    <option value="SAT">SAT</option>
                                    <option value="SUN">SUN</option>
                                </select>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="col mb-3">
                                <label>Hours</label>
                                <input type="text" className="form-control" id="hours"
                                       placeholder="Hours" required/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="col mb-3">
                                <label>Minutes</label>
                                <input type="text" className="form-control" id="minutes"
                                       placeholder="Minutes" required/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="col mb-3">
                                <label>Seconds</label>
                                <input type="text" className="form-control" id="seconds"
                                       placeholder="Seconds" required/>
                            </div>
                        </div>
                        <p style={{color: "red"}}>{this.state.message}</p>
                        <br/>
                        <button type="submit" className="btn btn-primary btn-lg mb-3 mr-3">Update</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default SchedulerView;