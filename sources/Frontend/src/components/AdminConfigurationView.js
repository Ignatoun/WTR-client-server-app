import React from "react";
import Menu from "./componentsMyReports/Menu";
import { IoIosPersonAdd } from 'react-icons/io';
import { TiUserDelete } from 'react-icons/ti';
import { FaUserEdit } from 'react-icons/fa';
import { IconContext } from "react-icons";
import {Link} from "react-router-dom";
import Footer from "./componentsMyReports/Footer";
import Loader from "./Loader";
import classNames from "classnames";
import {searchUsers} from "../routes/usersRoutes";

class AdminConfigurationView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentPage: 1,
            totalPages: 0,
            allUsers: [],
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.users.allUsers.length === 0 && this.props.users.allUsers.length)
            this.createStructureOfUsers(this.props.users.allUsers);
    }

    componentDidMount() {
        this.props.getUsers(this.props.users.currentUser);
    }

    createStructureOfUsers = (users) => {
        let row = [];
            let newArray = [];
            for (let i = 0; i < users.length; i++) {
                if (i % 12 === 0) {
                    row = [];
                    newArray.push(row);
                }
                row.push(users[i]);
            }
            this.setState({
                totalPages: Math.ceil(users.length / 12),
                allUsers: newArray,
            });
    };

    nextPage = () => {
        if (this.state.currentPage < this.state.totalPages) {
            this.setState({
                currentPage: this.state.currentPage + 1
            });
        }
    };

    prevPage = () => {
        if (this.state.currentPage > this.state.totalPages) {
            this.setState({
                currentPage: this.state.currentPage - 1
            });
        }
    };

    onSearching = event => {
        event.preventDefault();
        const [firstName, lastName] = document.getElementById("searching").value.split(" ");
        searchUsers(firstName, lastName || "", this.props.users.currentUser.token)
            .then(res => res.json())
            .then(data => this.createStructureOfUsers(data));
    };

    render() {
        return (
            <div>
                <Menu currentUser={this.props.users.currentUser}/>
                {this.props.users.loading ? <Loader/> : ""}
                <div className="container">
                    <form className="form-inline" onSubmit={this.onSearching}>
                        <div className="form-group mb-2">
                            <input type="text" className="form-control" id="searching"
                                   placeholder="Searching"/>
                        </div>
                        <button type="submit" className="btn btn-primary mb-2 ml-1">Search</button>
                    </form>
                    <div className="row">
                        {this.state.allUsers.length !== 0 ?this.state.allUsers[this.state.currentPage - 1].map(user => {
                            return (
                                <div key={user.userId} className="card shadow" style={{width: "12rem", margin: "4%"}}>
                                <img src="..." className="card-img-top" alt="..."/>
                                    <div className="card-body">
                                        <h5 className="card-title">{user.userName}</h5>
                                        <p className="card-text">{`${user.lastName} ${user.firstName}`}</p>
                                        <small>{user.title.titleName}</small>
                                    </div>
                                    <div className="card-footer">
                                        <div className="row">
                                            <div className="col-2">
                                                <Link to={`/update-user/${user.userId}`}>
                                                    <IconContext.Provider value={{ color: "blue", size: "1.5rem",
                                                        style: {cursor: "pointer"}}}>
                                                        <div style={{width: "2%", paddingLeft: "10%"}}>
                                                            <FaUserEdit />
                                                        </div>
                                                    </IconContext.Provider>
                                                </Link>
                                            </div>
                                            <div className="offset-7"/>
                                            <div className="col-2">
                                                <IconContext.Provider value={{ color: "red", size: "1.5rem", style: {cursor: "pointer"}}}>
                                                    <div style={{width: "2%", paddingLeft: "80%"}}
                                                         onClick={() => {
                                                             this.props.deleteUser(this.props.users.allUsers,
                                                                 user.userId, this.props.users.currentUser);
                                                             this.createStructureOfUsers(this.props.users.allUsers);
                                                         }}>
                                                        <TiUserDelete />
                                                    </div>
                                                </IconContext.Provider>
                                            </div>
                                        </div>
                                    </div>
                            </div>
                        )
                        }) : ""}
                        <div className="card" style={{width: "12rem", margin: "4%"}}>
                            <div className="card-body text-center">
                                <Link to="/new-user">
                                    <IconContext.Provider value={{ color: "blue", size: "3rem" }}>
                                        <div>
                                            <IoIosPersonAdd />
                                        </div>
                                    </IconContext.Provider>
                                </Link>
                                <p className="card-text">Add user</p>
                            </div>
                        </div>
                    </div>
                    <div className="mb-4">
                        <ul className="list-group list-group-horizontal-md">
                            <li className={
                                classNames("list-group-item", {"active": this.state.currentPage > 1})}
                                style={{cursor: "pointer"}} onClick={this.prevPage}>Назад</li>
                            <li className=
                                    {classNames("list-group-item", {"active": this.state.currentPage === 1})}
                            >1</li>
                            <li className="list-group-item"
                                style={this.state.currentPage - 1 > 1 ? {display: "inline"} : {display: "None"}}
                            >...</li>
                            <li className="list-group-item active"
                                style={this.state.currentPage !== 1 && this.state.currentPage !== this.state.totalPages ?
                                    {display: "inline"} : {display: "None"}}
                            >{this.state.currentPage}</li>
                            <li className="list-group-item"
                                style={this.state.totalPages - this.state.currentPage > 1 ? {display: "inline"} : {display: "None"}}
                            >...</li>
                            {this.state.totalPages !== 1 ? <li className=
                                     {classNames("list-group-item", {"active": this.state.currentPage === this.state.totalPages})}
                            >{this.state.totalPages}</li> : ""}
                            <li className={
                                classNames("list-group-item", {"active": this.state.currentPage < this.state.totalPages})}
                                style={{cursor: "pointer"}} onClick={this.nextPage}>Вперед</li>
                        </ul>
                    </div>
                </div>
            <Footer/>
            </div>
        );
    }
}

export default AdminConfigurationView;
