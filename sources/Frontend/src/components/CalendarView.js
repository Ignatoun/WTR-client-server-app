import React from "react";
import classNames from "classnames";
import {Link} from "react-router-dom";
import Loader from "../components/Loader";
import Menu from "./componentsMyReports/Menu";
import Footer from "./componentsMyReports/Footer";

const MONTH = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"];

const arrOfWeeksInMonth = (date) => {
    const countOfDays = 32 - new Date(date.getFullYear(), date.getMonth(), 32).getDate();
    const arrOfWeeks = [];
    let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).getDay();
    firstDay = firstDay === 0 ? 7 : firstDay;
    let week = new Array(7).fill(0);
    for (let i = 1, j = firstDay; i <= countOfDays; i++, j++) {
        week[j - 1] = i;
        if (j === 7 || i === countOfDays) {
            arrOfWeeks.push(week);
            week = Array(7).fill(0);
            j = 0;
        }
    }
    return arrOfWeeks;
};

const isCurrentDay = (date, day) => {
    let currentDate = new Date();
    return date.getFullYear() === currentDate.getFullYear() &&
        date.getMonth() === currentDate.getMonth() &&
        day === currentDate.getDate();
};

class CalendarView extends React.Component{
    constructor(props) {
        super(props);

        this.state = {
            date: new Date(),
        };
    }

    componentDidMount() {
        this.props.getReports(this.state.date, this.props.currentUser);
    }

    setNextMonth = () => {
        let currentMonth = this.state.date.getMonth() === 11 ? 0 : this.state.date.getMonth() + 1;
        let newYear = currentMonth === 0 ? this.state.date.getFullYear() + 1 : this.state.date.getFullYear();
        this.setState({
            date: new Date(newYear, currentMonth, 1)
        });
        this.props.getReports(new Date(newYear, currentMonth, 1), this.props.currentUser);
    };

    setPrevMonth = () => {
        let currentMonth = this.state.date.getMonth() === 0 ? 11 : this.state.date.getMonth() - 1;
        let newYear = currentMonth === 11 ? this.state.date.getFullYear() - 1 : this.state.date.getFullYear();
        this.setState({
            date: new Date(newYear, currentMonth, 1)
        });
        this.props.getReports(new Date(newYear, currentMonth, 1), this.props.currentUser);
    };

    setCurrentMonth = () => {
        this.setState({
            date: new Date()
        });
        this.props.getReports(new Date(), this.props.currentUser);
    };

    hasTask = (day) => {
        let currentDate = new Date(`${this.state.date.getMonth() + 1}-${day}-${this.state.date.getFullYear()}`);
        return this.props.reportDetails.currentMonth.includes(currentDate.valueOf());
    };

    isEarlier = (day) => {
        return new Date().valueOf() > new Date(this.state.date.getFullYear(), this.state.date.getMonth(), day).valueOf();
    };

    render() {
        return (
            <div>
                <Menu currentUser={this.props.currentUser}/><br/>
                <div className="container shadow-lg calendar">
                    <div className="row">
                        <div className="col-12">
                            <div className="row py-3 ">
                                <div className="col-9">
                                    <h2 className="m-0">
                                        {
                                            [MONTH[this.state.date.getMonth()], this.state.date.getFullYear()].join(" - ")
                                        }
                                    </h2>
                                </div>
                                <div className="col-1 btn-month">
                                    <button type="button" className="btn btn-success"
                                            onClick={this.setPrevMonth}>
                                        Prev
                                    </button>
                                </div>
                                <div className="col-1 btn-month">
                                    <button type="button" className="btn btn-danger"
                                            onClick={this.setCurrentMonth}>
                                        Current
                                    </button>
                                </div>
                                <div className="col-1 btn-month">
                                    <button type="button" className="btn btn-success"
                                            onClick={this.setNextMonth}>
                                        Next
                                    </button>
                                </div>
                            </div>
                            {this.props.reportDetails.request ? <Loader/>: ""}
                            <table className="table table-bordered text-center">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Mon</th>
                                    <th scope="col">Tue</th>
                                    <th scope="col">Wed</th>
                                    <th scope="col">Thu</th>
                                    <th scope="col">Fri</th>
                                    <th scope="col">Sat</th>
                                    <th scope="col">Sun</th>
                                </tr>
                                </thead>
                                <tbody>
                                {arrOfWeeksInMonth(this.state.date).map((week, index) => {
                                    return (
                                        <tr key={index}>
                                            <th scope="row" style={{cursor:"pointer"}} className="py-3">
                                                <Link
                                                    style={{
                                                        color: "black",
                                                        textDecoration: 'none'
                                                    }}
                                                    to={`/weekly/${this.state.date.getFullYear()}/${this.state.date.getMonth()}/${index}`}
                                                >
                                                    w{index + 1}
                                                </Link></th>
                                            {week.map((day, index) => {
                                                return day === 0 ? <td key={index}> </td> :
                                                    <td
                                                        key={index}
                                                        className={
                                                            classNames({
                                                                "table-success": this.isEarlier(day) && !isCurrentDay(this.state.date, day),
                                                                "table-danger": this.hasTask(day) && this.isEarlier(day) && !isCurrentDay(this.state.date, day),
                                                                "p-0":true
                                                            })
                                                        }
                                                        style={{
                                                            cursor:"pointer",
                                                            color: isCurrentDay(this.state.date, day) ? "white" : "black"
                                                        }}
                                                    >
                                                        <Link
                                                            style={{
                                                                color: "black",
                                                                textDecoration: 'none'
                                                            }}
                                                            to={`/daily/${this.state.date.getFullYear()}/${this.state.date.getMonth()}/${day}`}
                                                        >
                                                            <h5 style={{width: "100%"}} className={classNames({
                                                                "border border-warning text-center":  isCurrentDay(this.state.date, day),
                                                                "d-inline-block text-center m-0 py-3":true
                                                            })}>{day}</h5>
                                                        </Link>
                                                    </td>;
                                            })}
                                        </tr>
                                    )
                                })}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <Footer/>
            </div>
        )
    }
}

export default CalendarView;
