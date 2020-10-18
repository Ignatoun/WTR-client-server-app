import React from "react";
import Footer from "./componentsMyReports/Footer";
import Menu from "./componentsMyReports/Menu";
import {Redirect} from "react-router-dom"

class UpdateUserView extends React.Component{
    constructor(props) {
        super(props);

        this.state = {
            updated: false,
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.users.allUsers.length === 0 && this.props.users.allUsers.length) {
            const user = this.props.users.allUsers.find(user => user.userId === +this.props.params.userId);
            document.getElementById("firstName").value = user.firstName;
            document.getElementById("lastName").value = user.lastName;
            document.getElementById("username").value = user.userName;
            document.getElementById("email").value = user.email;
            document.getElementById("title").value = user.title.titleId;
            document.getElementById("department").value = user.department.departmentId;
        }
    }

    componentDidMount() {
        this.props.getDeparments(this.props.users.currentUser);
        this.props.getTitles(this.props.users.currentUser);
        this.props.getUsers(this.props.users.currentUser);
    }

    onSubmit = event => {
        event.preventDefault();
        const newUser = {
            userId: this.props.params.userId,
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            userName: document.getElementById("username").value,
            email: document.getElementById("email").value,
            titleId: document.getElementById("title").value,
            departmentId: document.getElementById("department").value,
            password: document.getElementById("password").value,
            roleId: [],
        };
        this.props.updateUser(this.props.users.allUsers, newUser, this.props.users.currentUser);
        setTimeout(() => this.setState({
            updated: true,
        }), 100);
    };

    render() {
        console.log(this.props);
        if (this.state.updated)
            return <Redirect to="/configuration"/>;
        return (
            <div>
                <Menu currentUser={this.props.users.currentUser}/>
                <div className="container">
                    <div className="row">
                        <div className="col-12 shadow">
                            <br/>
                            <div className="row">
                                <div className="col-6">
                                    <h2>
                                        Update user
                                    </h2>
                                    <br/>
                                </div>
                                <div className="offset-1"/>
                            </div>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label htmlFor="inputStartDate">First name</label>
                                        <input type="text" className="form-control" id="firstName"
                                               placeholder="First name" required/>
                                    </div>
                                    <div className="col mb-3">
                                        <label htmlFor="inputEndDate">Last name</label>
                                        <input type="text" className="form-control" id="lastName"
                                               placeholder="Last name" required/>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label htmlFor="inputStartDate">Username</label>
                                        <input type="text" className="form-control" id="username"
                                               placeholder="Username" required/>
                                    </div>
                                    <div className="col mb-3">
                                        <label htmlFor="inputEndDate">Email</label>
                                        <input type="email" className="form-control" id="email"
                                               placeholder="Email" required/>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label htmlFor="inputStartDate">Password</label>
                                        <input type="text" className="form-control" id="password"
                                               placeholder="password" required/>
                                    </div>
                                </div>
                                <div className="form-row">
                                    <div className="col mb-3">
                                        <label>Title</label>
                                        <select className="custom-select" id="title" required>
                                            <option selected disabled value="">Choose...</option>
                                            {this.props.titles.map(title => {
                                                return <option
                                                    key={title.titleId} value={title.titleId}>
                                                    {title.titleName}
                                                </option>
                                            })}
                                        </select>
                                    </div>
                                    <div className="col mb-3">
                                        <label>Department</label>
                                        <select className="custom-select" id="department" required>
                                            <option selected disabled value="">Choose...</option>
                                            {this.props.departments.map(department => {
                                                return <option
                                                    key={department.departmentId} value={department.departmentId}>
                                                    {department.departmentName}
                                                </option>
                                            })}
                                        </select>
                                    </div>
                                </div>
                                <br/>
                                <button type="submit" className="btn btn-primary btn-lg mb-3 mr-3">Update</button>
                            </form>
                        </div>
                    </div>
                    <br/>
                </div>
                <Footer/>
            </div>
        )
    }
}

export default UpdateUserView;
