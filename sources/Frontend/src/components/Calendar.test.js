import React from "react"
import {shallow,configure} from "enzyme"
import Calendar from "./CalendarView"
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter() })
const initialState = {
    currentMonth: [],
    copy: {},
    request: false,
};
function getRep(x){
    return x;
}
const arr = [2,4,5,6]
describe("Calendar test",() => {
    it("Render component",() => {
        const wrapper = shallow(<Calendar reportDetails = {initialState} getReports={getRep}/>);
        expect(wrapper.exists()).toBe(true);
    })

    it("Check currentMonth func()",() => {
    const wrapper = shallow(<Calendar reportDetails = {initialState} getReports={getRep}/>);
    let component = wrapper.find('.btn-danger');
    expect(component.length).toBe(1);
    component.simulate('click');
    expect(wrapper.state().date).not.toBeNull;
    })
})
