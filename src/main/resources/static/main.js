users = getUsers();
let tbody = document.getElementById("usersTableContent");


// получение юзеров и передача в метод заполнения таблицы
async function getUsers() {
    let response = await fetch("http://localhost:8080/admin/users");
    if (response.ok) {
        let data = await response.json();
        clearTable(tbody);
        data.forEach(e => usersTableInsert(e, tbody))
        return data
    } else {
        alert('error', response.status);
    }
}

//очистка таблицы
function clearTable(tbody) {
    while (tbody.childNodes.length) {
        tbody.removeChild(tbody.childNodes[0]);
    }
}

//заполнение таблицы
function usersTableInsert(e, tbody) {

    let tr = document.createElement('tr');
    tr.innerHTML = `
                                <td>${e.user.id}</td>
                                <td>${e.user.name}</td>
                                <td>${e.user.surname}</td>
                                <td>${e.user.age}</td>
                                <td>${e.user.city}</td>
                                <td>${e.user.email}</td>
                                <td>${e.roles}</td>
                                <td>
                                                   <!-- Button trigger modal -->
                                      <button type="button" class="btn btn-info" data-bs-toggle="modal"  onclick="showEditModal(${e.user.id})"
                                       data-action="edit" data-bs-target="#editUser">
                                             Edit
                                       </button>
                                </td>
                                <td>
                                     <button type="button" class="btn btn-danger" data-bs-toggle="modal" onclick="showDeleteModal(${e.user.id})"
                                       data-action="delete" data-bs-target="#deleteUser">
                                                        Delete
                                       </button>

                               </td>
                                                            
                               
                               
                             
    `
    tbody.append(tr);
}

// создание и настрока кнопки создания
let userCreateButton = document.getElementById("newUserButton");
userCreateButton.onclick = function () {
    let u = createUser();
    postUser(u);
    document.getElementById("name").value = "";
    document.getElementById("surname").value = "";
    document.getElementById("age").value = "";
    document.getElementById("password").value = "";
    document.getElementById("city").value = "";
    document.getElementById("email").value = "";
}

// создание юзера из данных new user
function createUser() {
    return {
        user: {
            name: document.getElementById("name").value,
            surname: document.getElementById("surname").value,
            age: document.getElementById("age").value,
            password: document.getElementById("password").value,
            city: document.getElementById("city").value,
            email: document.getElementById("email").value
        },
        roles: Array.from(document.getElementById("dropDownList").selectedOptions).map(el => el.value)
    };
}

// запрос на сервер с добалением нового юзера
async function postUser(UserDto) {
    let response = await fetch("http://localhost:8080/admin/users", {
        method: 'POST',
        body: JSON.stringify(UserDto),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

    });
    if (response.ok) {
        updateAndShowUsers();
    } else {
        let error = await response.json();
        console.log(error)
        alert(JSON.stringify(error.message), response.status);
    }
}

//метод для обновления страницы без перезагрузок
function updateAndShowUsers() {
    var usersTab = document.getElementById("home-tab"); // theTabID of the tab you want to open
    var tab = new bootstrap.Tab(usersTab);
    tab.show();
    getUsers();
}


//получение юзера по ид
async function getUser(id) {
    let response = await fetch("http://localhost:8080/admin/users/" + id);
    return await response.json();
}


//////////////////тут  всё по удалению

// заполнение модальной формы delete
async function showDeleteModal(id) {
    let userDTO = await getUser(id);
    let form = document.forms["deleteForm"];
    form.id.value = userDTO.user.id;
    form.name.value = userDTO.user.name;
    form.surname.value = userDTO.user.surname;
    form.city.value = userDTO.user.city;
    form.age.value = userDTO.user.age;
    form.email.value = userDTO.user.email;

}

//запрос на удаление юзера
async function deleteRequest(id) {
    let response = await fetch("http://localhost:8080/admin/users/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (response.ok) {
        getUsers();
    } else {
        let error = await response.json();
        console.log(JSON.stringify(error.message), response.status)
    }

}

let nestedModalDeleteButton = document.getElementById("nestedModalDeleteButton");


//отключение перезагрузки страницы при отправке формы удаления
let formDelete= document.getElementById("deleteForm");

function handleForm(event) {
    event.preventDefault();
}

formDelete.addEventListener('submit', handleForm);

//кнопка delete внутри модального окна
nestedModalDeleteButton.onclick = function () {
    let form = document.forms["deleteForm"];
    console.log(form.id.value)
    let response = deleteRequest(form.id.value);


}
//////////////////конец кода по удалению

//////////////////тут  всё по редактированию юзера


// заполнение модальной формы edit
async function showEditModal(id) {
    let userDTO = await getUser(id);
    let form = document.forms["editForm"];
    form.id.value = userDTO.user.id;
    form.name.value = userDTO.user.name;
    form.surname.value = userDTO.user.surname;
    form.city.value = userDTO.user.city;
    form.age.value = userDTO.user.age;
    form.email.value = userDTO.user.email;
    form.password.value = userDTO.user.password;


    let userRoles = userDTO.roles;
// заполнение существующих ролей
    Array.from(form.roles).forEach(e=>
        {
            if (userRoles.includes(e.text)){
                e.selected = true;
            }

        }
    );

}

//запрос на обновление юзера
async function patchUser(UserDto) {
    let response = await fetch("http://localhost:8080/admin/users", {
        method: 'PATCH',
        body: JSON.stringify(UserDto),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

    });
    if (response.ok) {
        //очистка выбранных полей в списке ролей
        clearRolesSelect();
        // запрос на обновление таблицы
        getUsers();
    } else {
        let error = await response.json();
        alert(JSON.stringify(error.message), response.status);
    }
}

let nestedModalEditButton = document.getElementById("nestedModalEditButton");

//отключение перезагрузки страницы при отправке формы редактирования
let formEdit = document.getElementById("editForm");
formEdit.addEventListener('submit', handleForm);

//кнопка edit внутри модального окна
nestedModalEditButton.onclick = function () {
    let response = patchUser(createEditedUser());


}

// создание юзера из данных формы edit
function createEditedUser() {
    let form = document.forms["editForm"];
    return {
        user: {
            id:form.id.value,
            name:form.name.value,
            surname: form.surname.value,
            age: form.age.value,
            password: form.password.value,
            city: form.city.value ,
            email: form.email.value
        },
        roles: Array.from(form.roles.selectedOptions).map(el => el.value)
    };
}

//функция для очистки выбранных полей в списке ролей
function clearRolesSelect() {
    let form = document.forms["editForm"];
    Array.from(form.roles).forEach(e=> e.selected = false);
}

