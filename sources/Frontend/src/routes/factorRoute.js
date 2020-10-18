export async function getFactors(token) {
    return await (await fetch(`/user/factors`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
