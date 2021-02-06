/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var server = "/";

function search (){
    var searchTerm = $("#searchText").val().trim();
    if (searchTerm != "") {
        $("#people").show();
        $("#people").html("Searching...");
        $.ajax({
            url: server + "speakers/" +
                $("#searchType").val() + "/" +
                encodeURIComponent(searchTerm),
            method: "GET"
        }).done(
            function(data) {
                $("#people").empty();
                $("#people").hide();
                if (data.length == 0) {
                    $("#people").html("");
                    $("#notFound").show();
                    $("#notFound").html("No speaker(s) matching your search criteria");
                } else {
                    showResults(data);
                }
                $("#people").show(400, "swing");
            });
    } else {
        loadSpeakers();
    }
}

$(function() {   
    $("#searchText").on("keyup", function(e) {
        if (e.keyCode == 13) {
            search ();
        }
    });
});

function showResults(data){
    $("#people").hide();
    $("#people").empty();
    $("#notFound").hide();
    data.forEach(function(speaker) {
        var item = $(renderSpeakers(speaker));
/*        item.on("click", function() {
            var detailItem = $(renderSpeakerDetails(speaker));
            $("#home").hide();
            $("#detail").empty();                                   
            $("#notFound").hide();
            $("#detail").append(detailItem);
            $("#people").hide(
                400,
                "swing",
                function() {
                    $("#detail").show()
                });
        });*/
        $("#people").append(item);
    });
}

function showSpeakerForm() {
    $("#notFound").hide();
    $("#editForm").hide();
    $("#deleteButton").hide();
    $("#speakerForm").show();
    $("#formTitle").text("Add Speaker");
    $("#people").hide();
}

function loadSpeakers() {
    $("#notFound").hide();
    $("#searchText").val("");
    $("#speakerForm").hide();
    $("#editForm").hide();
    $("#home").show();
    $("#people").show();
    $("#people").html("Loading...");
    $.ajax({
        dataType: "json",
        url: server + "speakers",
        method: "GET"
    }).done(function(data) {
        showResults(data); 
        $("#people").show(400, "swing");
    });
}

function renderSpeakers(speaker){
    var template = $('#speakers_template').html();
    Mustache.parse(template);
    var rendered = Mustache.render(template, {
        "firstName" : speaker.firstName,
        "lastName" : speaker.lastName,
        "id" : speaker.id,
        "title" : speaker.title,
        "company" : speaker.company,
        "track" : speaker.track
    });
    return rendered;
}

function renderSpeakerDetails(speaker){
    var template = $('#detail_template').html();
    Mustache.parse(template);
    var rendered = Mustache.render(template,{
        "id" : speaker.id,
        "firstName" : speaker.firstName,
        "lastName" : speaker.lastName,
        "title" : speaker.title,
        "company" : speaker.company,
        "track" : speaker.track
    });
    return rendered;
}

function save() {
    var speaker = {
        id: "",
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        title: $("#title").val(),
        company: $("#company").val(),
        track: $("#track").val()
    };
    $.ajax({
        url: server + "speakers",
        method: "POST",
        data: JSON.stringify(speaker)
    }).done(function(data) {
        $("#detail").hide();
        $("#firstName").val("");
        $("#lastName").val("");
        $("#title").val("");
        $("#company").val("");
        $("#track").val("");
        loadSpeakers();
    });

}

function updateSpeaker() {
    var speaker = {
        id: $("#editId").val(),
        firstName: $("#editFirstName").val(),
        lastName: $("#editLastName").val(),
        title: $("#editTitle").val(),
        track: $("#editTrack").val(),
        company: $("#editCompany").val()
    };
    $("#detail").html("UPDATING...");
    $.ajax({
        url: server + "speakers/" + speaker.id,
        method: "PUT",
        data: JSON.stringify(speaker)
    }).done(function(data) {
        $("#detail").hide();
        loadSpeakers();
    });
}

function deleteSpeaker() {
    var speaker = {
        firstName: $("#editFirstName").val(),
        lastName: $("#editLastName").val()
    };
    $('<div></div>').dialog({
        modal: true,
        title: "Confirm Delete",
        open: function() {
            var msg = 'Are you sure you want to delete ' +
                speaker.firstName + ' ' + speaker.lastName + '?';
            $(this).html(msg);
        },
        buttons: {
            Ok: function() {
                $("#detail").html("DELETING...");
                $(this).dialog("close");
                $.ajax({
                    url: server + "speakers/" + speaker.id,
                    method: "DELETE"
                }).done(function(data) {
                    $("#detail").hide();
                    loadSpeakers();
                });
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });

}
