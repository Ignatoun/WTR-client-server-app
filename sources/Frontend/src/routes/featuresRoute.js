export async function getFeatures(token) {
    return await (await fetch(`/user/features`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
