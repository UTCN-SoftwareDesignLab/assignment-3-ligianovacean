function displayUsers(users) {
    var $tbody = $('tbody');
    $tbody.empty();
    for(var i in users) {
        var user = users[i];
        var $row = $('<tr>');
        $('<td>').html(user.username).appendTo($row);
        $('<td>').html(user.role).appendTo($row);

        $row.appendTo($tbody);
    }
}

function refreshUsers() {
    $.get('/admin/users', {}, function(result) {
        displayUsers(result);
    });
}

function createUser(user){
     $.ajax('/admin/createUser', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(user),
         dataType: 'json',
         success: function(result) {
             $('#error').val(result.message);
             $('#createUsername, #createPassword').val('');
             refreshUsers();
         }
     });
}


function updateUser(user){
     $.ajax('/admin/updateUser', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(user),
         dataType: 'json',
         success: function(result) {
             $('#error').val(result.message);
             $('#updatePassword').val('');
             refreshUsers();
         }
     });
}

function deleteUser(user){
     $.ajax('/admin/deleteUser', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: user,
         success: function(result) {
             refreshUsers();
         }
     });
}


$(function() {
    refreshUsers();
    $('button').click(function() {
        if (this.id == "createUser") {
            createUser({
                'username':     $('#createUsername').val(),
                'password':     $('#createPassword').val(),
                'role':         $('#createRole').val()
            });
        };
        if (this.id == "updateUser") {
            updateUser({
                'username':     $('#updateUsername').val(),
                'password':     $('#updatePassword').val()
            })
        };
        if (this.id == "deleteUser") {
            deleteUser({
                'username':     $('#deleteUsername').val()
            })
        };
        return false;
    });
});