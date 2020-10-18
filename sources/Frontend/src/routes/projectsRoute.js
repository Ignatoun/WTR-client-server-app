export async function getProjects(token) {
    return await (await fetch(`/user/projects`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
