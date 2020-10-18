import React from "react"
import {Redirect} from "react-router-dom"
import Loader from "./Loader";

class LoginForm extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        logged:false
    };

    this.onButtonClickLogged = this.onButtonClickLogged.bind(this);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.users.currentUser.username === undefined && this.props.users.currentUser.username)
            this.setState({logged: true});
    }

    onButtonClickLogged(event){
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        this.props.authorization({username, password});

    }
    componentDidMount() {
        //this.refs.username.focus();
    }

    render() {
        if(this.state.logged === true)
            return <Redirect to="/MyReports" />;
        return (
            <div className="col-12">
                <div className="container">
                    {this.props.users.loading ? <Loader/> : "" }
                    <div className="row">
                        <div className="offset-3">
                            <div className="form-login-container">
                                <form className="form-login" onSubmit={this.onButtonClickLogged}>
                                    <h1 className="h3 mb-3 font-weight-normal text-center">
                                        WTR Lite
                                    </h1>
                                    <div className="form-group">
                                        <label htmlFor="username">User</label>
                                        <input type="text"
                                               name="username"
                                               id="username"
                                               className="form-control"
                                               placeholder="Username"
                                               ref="username"
                                               required
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="password">Password</label>
                                        <input type="password"
                                               name="password"
                                               id="password"
                                               className="form-control"
                                               placeholder="Password"
                                               required
                                        />
                                    </div>
                                    <p style={{color: "red"}}>{this.props.users.message}</p>
                                    <button type="submit" className="btn btn-lg btn-primary btn-block">
                                        Sign in
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default LoginForm;
