var stompClient = null;

function connect(){
    var socket = new SockJS('/clinic-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

function disconnect(){
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function displayPatients(patients) {
     var $tbody = $('#patientsTable');
     $tbody.empty();
     for(var i in patients) {
         var patient = patients[i];
         var $row = $('<tr>');
         $('<td>').html(patient.pnc).appendTo($row);
         $('<td>').html(patient.name).appendTo($row);
         $('<td>').html(new Date(patient.dateOfBirth).toDateString()).appendTo($row);
         $('<td>').html(patient.address).appendTo($row);
         $row.appendTo($tbody);
     }
}

function displayConsultations(consultations) {
     var $tbody = $('#consultationsTable');
     $tbody.empty();
     for(var i in consultations) {
         var consultation = consultations[i];
         var $row = $('<tr>');
         $('<td>').html(consultation.id).appendTo($row);
         $('<td>').html(new Date(consultation.date)).appendTo($row);
         $('<td>').html(consultation.doctor.username).appendTo($row);
         $('<td>').html(consultation.patient.pnc).appendTo($row);
         $row.appendTo($tbody);
     }
}

function refreshPatients() {
    $.get('/patients', {}, function(result) {
        displayPatients(result);
    });
}

function refreshConsultations() {
    $.get('/consultations', {}, function(result) {
        displayConsultations(result);
    });
}

function createPatient(patient){
     $.ajax('/createPatient', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(patient),
         dataType: 'json',
         success: function(result) {
             $('#error').val(result.message);
             $('#createPnc, #createName, #createDate, #createAddress').val('');
             refreshPatients();
         }
     });
}

function updatePatient(patient){
     $.ajax('/updatePatient', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(patient),
         dataType: 'json',
         success: function(result) {
             $('#error').val(result.message);
             $('#updatePnc, #updateName, #updateDate, #updateAddress, #updatePnc').val('');
             refreshPatients();
         }
     });
}

function createCons(cons){
     $.ajax('/createConsultation', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(cons),
         dataType: 'json',
         success: function(result) {
             refreshConsultations();
         }
     });
}

function updateCons(cons){
     $.ajax('/updateConsultation', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(cons),
         dataType: 'json',
         success: function(result) {
             refreshConsultations();
         }
     });
}

function deleteCons(cons){
     $.ajax('/deleteConsultation', {
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: 'POST',
         data: JSON.stringify(cons),
         dataType: 'json',
         success: function(result) {
             refreshConsultations();
         }
     });
}

function checkIn(cons){
    $.ajax('/checkIn', {
             headers: {
                 'Accept': 'application/json',
                 'Content-Type': 'application/json'
             },
             type: 'POST',
             data: JSON.stringify(cons),
             dataType: 'json',
             success: function() {}
         });
}

$(function() {
    refreshPatients();
    refreshConsultations();
    $('button').click(function() {
        if (this.id == "createPatient") {
            createPatient({
                'pnc':     $('#createPnc').val(),
                'name':     $('#createName').val(),
                'dateOfBirth':         $('#createDate').val(),
                'address':      $('#createAddress').val()
            });
        };
        if (this.id == "updatePatient") {
            updatePatient({
                'id':       $('#updateId').val(),
                'pnc':      $('#updatePnc').val(),
                'name':     $('#updateName').val(),
                'dateOfBirth':         $('#updateDate').val(),
                'address':      $('#updateAddress').val()
            })
        };
        if (this.id == "createCons") {
            createCons({
                'date':     $('#createConsDate').val(),
                'patientId':    $('#createConsPatient').val(),
                'doctorUsername':       $('#createConsDoctor').val()
            });
        };
        if (this.id == "updateCons") {
            createCons({
                'id':       $('#updateConsId').val(),
                'date':     $('#updateConsDate').val(),
                'patientId':    $('#updateConsPatient').val(),
                'doctorUsername':       $('#updateConsDoctor').val()
            });
        };
        if (this.id == "deleteCons") {
             deleteCons($('#deleteConsId').val())
        };
        if (this.id == "checkIn"){
            checkIn({
                'id':   $('#consultation').val()
            });
        };
        return false;
    });
});