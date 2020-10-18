export async function getAllUsersManager(token) {
    return await (await fetch("/manager/users", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`

        }
    })).json();
}

export async function getAllUsers(token) {
    return await (await fetch("/manager/users", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`,
        }
    })).json();
}

export async function addNewUser(user, token) {
    return await (await fetch("/manager/users", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`,
        },
        body: JSON.stringify(user),
    })).json()
}

export async function deleteUserById(userId, token) {
    return await fetch(`/manager/users/${userId}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer_${token}`
        }
    });
}

export async function updateUserById(user, token) {
    return await (await fetch(`/manager/users/${user.userId}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer_${token}`
        },
        body: JSON.stringify(user),
    })).json();
}

export async function authorization(user) {
    return await (await fetch("/auth/login", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({username: user.username, password: user.password}),
    })).json();
}

export function searchUsers(firstName, lastName, token) {
    return fetch(`/manager/users/filter?firstName=${firstName}&secondName=${lastName}`,
        {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer_${token}`
            },
        });
}

