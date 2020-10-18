export async function getTasks(token) {
    return await (await fetch(`/user/tasks`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
