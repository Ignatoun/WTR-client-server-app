export async function getAllTitles(token) {
    return await (await fetch("/user/titles", {
        method: "GET",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    })).json();
}
