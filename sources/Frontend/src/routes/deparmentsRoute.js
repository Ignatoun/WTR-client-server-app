export async function getAllDepartments(token) {
    return await (await fetch("/user/departments", {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
