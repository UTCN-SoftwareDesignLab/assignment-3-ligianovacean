var stompClient = null;

function connect(){
    var socket = new SockJS('/clinic-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({},
        function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/queue/reply', function(message) {
                $("#notification").append("<tr><td>" + JSON.parse(message.body).content + "</td></tr>");
            });
        });
}

function disconnect(){
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function displayCons(cons) {
    var $tbody = $('tbody');
    $tbody.empty();
    for(var i in cons) {
        var con = cons[i];
        var $row = $('<tr>');
        $('<td>').html(con.id).appendTo($row);
        $('<td>').html(new Date(con.date)).appendTo($row);
        $('<td>').html(con.patient.pnc).appendTo($row);
        $('<td>').html(con.diagnosis).appendTo($row);
        $row.appendTo($tbody);
    }
}

function refreshCons() {
    $.get('/docConsultations', {}, function(result) {
        displayCons(result);
    });
}

function updateCons(cons){
     $.ajax('/updateDetails', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(cons),
         dataType: 'json',
         success: function(result) {
             $('#error').val(result.message);
             $('#diagnosis').val('');
             refreshCons();
         }
     });
}

$(function() {
    refreshCons();
    $('button').click(function() {
        if (this.id == "updateConsultation") {
            updateCons({
                'id':     $('#consultationId').val(),
                'diagnosis':     $('#diagnosis').val()
            })
        };
        return false;
    });
});