import React from "react"
import {shallow,configure} from "enzyme"
import LoginForm from "./LoginForm"
import Adapter from 'enzyme-adapter-react-16';
import { create } from "react-test-renderer";

configure({ adapter: new Adapter() })

describe("LoginForm.js",() => {
    it("Render component",() => {
        const wrapper = shallow(<LoginForm/>);
        expect(wrapper.exists()).toBe(true);
    })
    it("Render first <div/>",() => {
        const wrapper = shallow(<LoginForm/>);
        expect(wrapper.find("div").exists()).toBe(true);
    })
    it("Render first <div/>",() => {
            const wrapper = shallow(<LoginForm/>);
            expect(wrapper.find("header").exists()).toBe(false);
    })
    it("Check prop logged",() => {
        const wrapper = shallow(<LoginForm />);
        expect(wrapper.state().logged).toBe(false);
        expect(wrapper.find('.form-login').length).toBe(1);
        const component = wrapper.find('.btn-block').simulate("click");
        expect(wrapper.state().logged).toBe(true);

        //toEqual(expect.any(Function)
    })
})