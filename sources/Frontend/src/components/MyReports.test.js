import React from "react"
import {shallow,configure} from "enzyme"
import MyReports from "./MyReports.jsx"
import Footer from "./componentsMyReports/Footer"
import Header from "./componentsMyReports/Header"
import Menu from "./componentsMyReports/Menu"
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter() })

describe("MyReports pre-main component",() => {
    it("Render component",() => {
        const wrapper = shallow(<MyReports />);
        expect(wrapper.exists()).toBe(true);
    })
    it("check Footer component",() => {
        const wrapper = shallow(<MyReports />);
        expect(wrapper.find(Footer).exists()).toBe(true);
    })
    it("check Menu component",() => {
        const wrapper = shallow(<MyReports />);
        expect(wrapper.find(Menu).exists()).toBe(true);
    })
    it("check Header component",() => {
        const wrapper = shallow(<MyReports />);
        expect(wrapper.find(Header).exists()).toBe(true);
    })
})