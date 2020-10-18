// eslint-disable-next-line
import React from 'react';
import ReactDOM from 'react-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles/index.scss"
import "./styles/circular.css";
import { routing } from "./components/Routing";
import {Provider} from "react-redux";
import store from "./store";


ReactDOM.render(
    <Provider store={store}>
        {routing}
    </Provider>, document.getElementById('root'));
