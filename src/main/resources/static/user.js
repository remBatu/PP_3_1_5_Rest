getCurrentUser()


async function getCurrentUser() {
    let response = await fetch("http://localhost:8080/admin/user");
    if (response.ok) {
        let data = await response.json();
        console.log(data)
        hat(data)
        tableContent(data)
    } else {
        alert('error', response.status);
    }
}

function hat(e) {
    document.getElementById('hat').innerHTML = `<div class="hat-info">  ${e.user.name} with roles: ${e.roles}</div>`

}

function tableContent(e) {
    document.getElementById('tableContent').innerHTML = `
                                <td>${e.user.id}</td>
                                <td>${e.user.name}</td>
                                <td>${e.user.surname}</td>
                                <td>${e.user.age}</td>
                                <td>${e.user.city}</td>
                                <td>${e.user.email}</td>
                                <td>${e.roles}</td>
    `

}
