export async function getLocations(token) {
    return await (await fetch(`/user/locations`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
