/// <reference path="mediacontroller.ts"/>

var main: JQuery = null;
var channelId:string = getCookie('channel');
if (channelId == '')
    channelId = 'UCboMX_UNgaPBsUOIgasn3-Q';
var seriesFilters:JQuery = null;

$(function () {
    seriesFilters = $('#filterSeries').find('input[data-series-id]');
    seriesFilters.change(function (eventObject) {
        var seriesId = $(eventObject.target).attr('data-series-id');
        if (seriesId === 'all') {
            $('#filterSeries').find('input[data-series-id!="all"]').prop('checked', false);
        } else {
            $('#filterSeries').find('input[data-series-id="all"]').prop('checked', false);
        }
    });
    main = $('#main');
    main.css('min-height', ($(window).outerHeight() - 100) + 'px');
});

function onYouTubeIframeAPIReady() {
    getRandomVideo(MediaController.initPlayer);
}

function getRandomVideo(callback:(content:Content) => void) {
    $.ajax('/api/getRandomVideo/' + channelId,
        <JQueryAjaxSettings>{
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR:JQueryXHR, textStatus:string, errorThrown:string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data:any) {
                callback(Content.fromJSON(data))
            }
        }
    )
}

function getRandomPlaylist(callback:(playlist:Series) => void) {
    $.ajax('/api/getRandomPlaylist/' + channelId,
        <JQueryAjaxSettings>{
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR:JQueryXHR, textStatus:string, errorThrown:string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data:any) {
                callback(Series.fromJSON(data))
            }
        })
}

function getQueue(filters:string[], callback:(contents:Content[]) => void) {
    $.ajax('/api/getQueue/' + channelId,
        <JQueryAjaxSettings>{
            type: 'POST',
            data: JSON.stringify({
                filters: filters
            }),
            contentType: 'application/json',
            dataType: 'json',
            error: function (jqXHR:JQueryXHR, textStatus:string, errorThrown:string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data:any) {
                callback(_.map(data, function (contentObj:any, i:number) {
                    return Content.fromJSON(contentObj);
                }))
            }
        }
    )
}

function loadRandomVideo() {
    getRandomVideo(MediaController.loadContent)
}

function loadRandomPlaylist() {
    getRandomPlaylist(MediaController.loadPlaylist)
}

function loadQueue() {
    getQueue(MediaController.getSelectedFilters(), MediaController.loadQueue)
}

function getCookie(cname:string) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}