export function searchReportDetail(report, token) {
    let url = "/user/reportDetails/filter?";
    if(report.status)
        url += `status=${report.status}&`;
    if(report.projectId)
        url += `projectId=${report.projectId}&`;
    if(report.taskId)
        url += `taskId=${report.taskId}&`;
    if(report.detailedTaskId)
        url += `detailedTaskId=${report.detailedTaskId}&`;
    if(report.featureId)
        url += `featureId=${report.featureId}&`;
    if(report.factorId)
        url += `factorId=${report.factorId}&`;
    if(report.locationId)
        url += `locationId=${report.locationId}&`;
    if(report.dateStart)
        url += `dateStart=${report.dateStart}&`;
    if(report.dateEnd)
        url += `dateEnd=${report.dateEnd}&`;
    console.log(url);
    return fetch(url, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer_${token}`
            },
        });
}

export function searchReportDetailManager(report, token) {
    let url = "/manager/reportDetails/filter?";
    if(report.status)
        url += `status=${report.status}&`;
    if(report.projectId)
        url += `projectId=${report.projectId}&`;
    if(report.taskId)
        url += `taskId=${report.taskId}&`;
    if(report.detailedTaskId)
        url += `detailedTaskId=${report.detailedTaskId}&`;
    if(report.featureId)
        url += `featureId=${report.featureId}&`;
    if(report.factorId)
        url += `factorId=${report.factorId}&`;
    if(report.locationId)
        url += `locationId=${report.locationId}&`;
    if(report.dateStart)
        url += `dateStart=${report.dateStart}&`;
    if(report.dateEnd)
        url += `dateEnd=${report.dateEnd}&`;
    console.log(url);
    return fetch(url, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer_${token}`
            },
        });
}


export async function allReportDetailsManager(userId,token){
    return await (await fetch(`/manager/reportDetails/filter/${userId}`,{
    method: "GET",
    headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer_${token}`
    }
        })).json();
}

export async function getAllReportDetails(token){
    return await (await fetch("/manager/reportDetails",{
    method: "GET",
    headers: {
        "Authorization": `Bearer_${token}`
    }
        })).json();
}

export async function getReportDetails(token){
    return await (await fetch("/admin/reportDetails",{
    method: "GET",
    headers: {
        "Authorization": `Bearer_${token}`
    }
        })).json();
}
export async function getReportDetailsForMonth(month, year, token) {
    return await (await fetch(`/user/reportDetails/date/${month}/${year}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}

export function saveReports(reports, token) {
    return fetch(`/user/reportDetails/list`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`,
        },
        body: JSON.stringify(reports),
    })
        .then(res => res.json())
        .then(data => console.log(data));
}

export function updateReports(reports, token) {
    return fetch(`/user/reportDetails/list`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`,
        },
        body: JSON.stringify(reports),
    })
        .then(res => res.json())
        .then(data => console.log(data));
}

export function getReportsByDate(date, token) {
    return fetch(`/user/reportDetails/${date}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    });
}

export async function updateReportDetailById(reportDetail, token) {
    return await (await fetch(`/manager/reportDetails/${reportDetail.reportDetailsId}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`,
        },
        body: JSON.stringify(reportDetail),
    })).json();
}


