export async function getDetailedTasks(token) {
    return await (await fetch(`/user/detailedTasks`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
