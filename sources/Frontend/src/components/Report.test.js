import React from "react"
import {shallow,configure,generateWrapper} from "enzyme"
import WeeklyReport from "./WeeklyReport"
import {useParams} from "react-router-dom";

import DailyReport from "../containers/DailyReport"
import Adapter from 'enzyme-adapter-react-16'
import DailyReportForm from "./DailyReportForm"

configure({ adapter: new Adapter() })
const params = {
month:"",
day:"",
}
const arr = {
features:[
        [1,2,34,5],
        [1,2,34,5],
        [1,2,34,5]
        ],
tasks:[
      [1,2,34,5],
      [1,2,34,5],
      [1,2,34,5]
      ],
projects:{
         projects:[],
         request:false
         },
detailedTasks:[
      [1,2,34,5],
      [1,2,34,5],
      [1,2,34,5]
      ],
factors:[
      [1,2,34,5],
      [1,2,34,5],
      [1,2,34,5]
      ],
locations:[
          [1,2,34,5],
          [1,2,34,5],
          [1,2,34,5]
          ],
getLocations:function(){},
getProjects:function(){},
getFeatures:function(){},
getTasks:function(){},
getFactors:function(){},
getDetailedTasks:function(){},
selectProject:function(){},
deleteFeatures:function(){},
deleteTasks:function(){},
deleteDetailedTasks:function(){},
deleteProjects:function(){},
copyReportDetails:function(){}
}

const sign = <DailyReportForm params={params} projects={arr.projects} features={arr.features} tasks={arr.tasks}
 detailedTasks={arr.detailedTasks} factors={arr.factors} locations={arr.locations} getLocations={arr.getLocations}
 getProjects={arr.getProjects} getFeatures={arr.getFeatures} getTasks={arr.getTasks} getDetailedTasks={arr.getDetailedTasks}
 getFactors={arr.getFactors}/>
describe("Render reports",() => {


    it("Check weeklyReports",() => {

        const wrapper = shallow(<WeeklyReport params={params} />);
        expect(wrapper.exists()).toBe(true)
    })
    it("Check dailyReports",() => {
        const wrapper = shallow(sign);
        expect(wrapper.exists()).toBe(true);
    })
    it("check remove report func()", () => {
        const wrapper = shallow(sign);
        let component = wrapper.find(".btn-success");
        expect(component.length).toBe(1);
        expect(wrapper.state().currentKey).toBe(0);
        component.simulate('click');
        expect(wrapper.state().currentKey).toBe(1);
        let component2 = wrapper.find('.btn-danger');
        expect(component2.length).toBe(4);
        expect(component.length).toBe(1);
    })
    it("check add report func()", () => {
            const wrapper = shallow(sign);

            let component2 = wrapper.find(".btn-success");
            component2.simulate('click');
            let component = wrapper.find(".btn-danger");
            expect(component.length).toBe(4);

    })
//    it("Copy data on DailyReport ",() => {
//    const wrapper = shallow(sign);
//    let component = wrapper.find(".btn").at(5);
//    let project = wrapper.find("custom-select").at(4);
//    project.value = 2;
//    expect(project.value).toBe(2);
//    component.simulate("click");
//
//
//    })
    it("Clear data on DailyReport ",() => {
        const wrapper = shallow(sign);
        let comment = wrapper.find(".form-control").at(4)
        expect(comment.exists()).toBe(true)
        expect(comment.prop('value')).toBe("some txt");
        let buttonClear = wrapper.find(".btn-danger").at(0);
        buttonClear.simulate("click");
        expect(comment.prop('value')).toBeUndefined();





//                document.getElementsByName("selectProject")[index].value = "";
//                document.getElementsByName("selectFeature")[index].value = "";
//                document.getElementsByName("selectTask")[index].value = "";
//                document.getElementsByName("selectDetailedTask")[index].value = "";
//                document.getElementsByName("startDate")[index].value = "";
//                document.getElementsByName("endDate")[index].value = "";
//                document.getElementsByName("inputHours")[index].value = "";
//                document.getElementsByName("inputWorkUnits")[index].value = "";
//                document.getElementsByName("comments")[index].value = "";
//                document.getElementsByName("factors")[index].value = "1";
//                document.getElementsByName("locations")[index].value = "1";
    })
})
