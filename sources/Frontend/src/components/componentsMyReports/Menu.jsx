import React from "react";
import {NavLink} from 'react-router-dom';
import {Link} from "react-router-dom";

class Menu extends React.Component {
constructor(props){
super(props);
}
render(){
    return(
        <div>
            <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
                <h5 className="my-0 mr-md-auto font-weight-normal">
                    <Link className="p-2 text-dark" to="/MyReports" style={{textDecoration: 'none'}}>
                        Epolsoft
                    </Link>
                </h5>
                <nav className="my-2 my-md-0 mr-md-3">
                    <Link className="p-2 text-dark" to="/MyReports"  style={{textDecoration: 'none'}}>My reports</Link>
                    <Link className="p-2 text-dark" to="/search"  style={{textDecoration: 'none'}}>Search reports</Link>
                    <Link className="p-2 text-dark" to="#"  style={{textDecoration: 'none'}}>Help</Link>
                    {this.props.currentUser.roles.find(role => role.roleName === "ADMIN") ?
                        <Link className="p-2 text-dark" to="/configuration" style={{textDecoration: 'none'}}>Configuration</Link> : ""}
                    {this.props.currentUser.roles.find(role => role.roleName === "ADMIN") ?
                        <Link className="p-2 text-dark" to="/scheduler" style={{textDecoration: 'none'}}>Scheduler</Link> : ""}
                        {this.props.currentUser.roles.find(role => role.roleName === "MANAGER") ?
                            <Link className="p-2 text-dark" to="/manager/registered" style={{textDecoration: 'none'}}>View all reports</Link> : ""}
                </nav>
                <Link className="btn btn-outline-primary" to="/">Log out</Link>
            </div>
        </div>
        )
    }
}

export default Menu
