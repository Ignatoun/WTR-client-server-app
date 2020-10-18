import React from "react";
import {useParams} from "react-router-dom";
import Footer from "./componentsMyReports/Footer";
// import Menu from "./componentsMyReports/Menu";

const MONTH = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"];

function WeeklyReport() {
    const reports = [0,1,2,3,4,5,6];
    const params = useParams();
    console.log(params);
    return (
        <div>
            {/*<Menu/>*/}
            {reports.map(value => {
                return (
                    <div className="container">
                        <div className="row">
                            <div className="col-12 shadow">
                                <br/>
                                {/*<h4>{`Day: ${params.day}, Month: ${params.month}, Year: ${params.year}`}</h4>*/}
                                <div className="row">
                                    <div className="col-6">
                                        <h2>
                                            {
                                                [MONTH[params.month], 'w'+(Number(params.week)+1), params.year].join(" - ")
                                            }
                                        </h2>
                                    </div>
                                    <div className="offset-1"/>
                                </div>
                                <form>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Project</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                <option selected disabled value="">Choose...</option>
                                                <option>wtr lite for all companies and users</option>
                                                <option>the best game in the world!</option>
                                            </select>
                                        </div>
                                        <div className="col mb-3">
                                            <label htmlFor="inputStartDate">Start Date</label>
                                            <input type="text" className="form-control" id="inputStartDate" placeholder="2019-11-02"/>
                                        </div>
                                        <div className="col mb-3">
                                            <label htmlFor="inputEndDate">End Date</label>
                                            <input type="text" className="form-control" id="inputEndDate" placeholder="2020-05-09"/>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Feature</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                <option selected disabled value="">Choose...</option>
                                                <option>project database development</option>
                                                <option>application composition development</option>
                                                <option>connecting modules to the project</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Task</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                <option selected disabled value="">Choose...</option>
                                                <option>development of database tables</option>
                                                <option>database table implementation</option>
                                                <option>creating test data for the database</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Detailed Task</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                <option selected disabled value="">Choose...</option>
                                                <option>Creating database schema (fields)</option>
                                                <option>Creating database schema (relations)</option>
                                                <option>Creating SQL script for tests</option>
                                                <option>Fixing errors</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="form-group col-md-6 font-weight-bold">
                                            <label htmlFor="inputHours">Hours</label>
                                            <input type="text" className="form-control" id="inputHours" placeholder="8"/>
                                        </div>
                                        <div className="form-group col-md-6 font-weight-bold">
                                            <label htmlFor="inputWorkUnits">Work Units</label>
                                            <input type="text" className="form-control" id="inputWorkUnits" placeholder="8"/>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Factor</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                {/*<option selected disabled value="">Choose...</option>*/}
                                                <option selected>Standard</option>
                                                <option>Overtime</option>
                                                <option>Vacation</option>
                                                <option>Sick or Care Absence</option>
                                                <option>Business Trip</option>
                                                <option>Standard Night</option>
                                                <option>Excused Absence</option>
                                                <option>Day Off</option>
                                                <option>Unexcused Absence</option>
                                            </select>
                                        </div>
                                        <div className="col mb-3">
                                            <label>Location</label>
                                            <select className="custom-select" id="validationServer04" required>
                                                {/*<option selected disabled value="">Choose...</option>*/}
                                                <option selected>Brest</option>
                                                <option>Minsk</option>
                                                <option>Grodno</option>
                                                <option>Lodz</option>
                                                <option>Prague</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col mb-3">
                                            <label>Comment</label>
                                            <textarea className="form-control" aria-label="With textarea" placeholder="Your comment..."></textarea>
                                        </div>
                                    </div>
                                </form>


                            </div>
                        </div>
                        <br/>
                    </div>
                )
            })}
            <div className="container">
                <button type="button" className="btn btn-primary btn-lg mb-3 mr-3">Submit</button>
                <button type="button" className="btn btn-dark btn-lg mb-3 mr-3">Submit as Private</button>
                <button type="button" className="btn btn-secondary btn-lg mb-3 mr-3">Cancel</button>
            </div>
            <Footer/>
        </div>
    )
}

export default WeeklyReport;
