/// <reference path="mediacontroller.ts"/>

let main: JQuery = null;
let channelId: string = getCookie('channel');
if (channelId == '')
    channelId = 'UCboMX_UNgaPBsUOIgasn3-Q';
let seriesFilters: JQuery = null;
let filterGroups: JQuery = null;

$(function () {
    seriesFilters = $('#filterSeries').find('input[data-series-id]');
    seriesFilters.change(function (eventObject) {
        let seriesId = $(eventObject.target).attr('data-series-id');
        if (seriesId === 'all') {
            $('#filterSeries').find('input[data-series-id!="all"]').prop('checked', false);
        } else {
            $('#filterSeries').find('input[data-series-id="all"]').prop('checked', false);
        }
    });
    filterGroups = $('.filter');
    filterGroups.change(function (eventObject) {
        let input: JQuery = $(eventObject.target);
        let tag: string = input.attr('data-tag');
        let fieldset: JQuery = input.parent().parents().eq(1);
        if (tag === 'all') {
            fieldset.find('input[data-tag!="all"]').prop('checked', false);
        } else {
            fieldset.find('input[data-tag="all"]').prop('checked', false);
        }
    });
    main = $('#main');
    main.css('min-height', ($(window).outerHeight() - 100) + 'px');
    $(window).resize(function (evt) {
        if (MediaController.player)
            MediaController.player.setSize(main.width(), main.width() * (9 / 16))
    })
});

function onYouTubeIframeAPIReady() {
    getRandomVideo(MediaController.initPlayer);
}

function getRandomVideo(callback: (content: Content) => void) {
    $.ajax('/api/getRandomVideo/' + channelId,
        <JQueryAjaxSettings>{
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR: JQueryXHR, textStatus: string, errorThrown: string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data: any) {
                callback(Content.fromJSON(data))
            }
        }
    )
}

function getRandomPlaylist(callback: (playlist: Series) => void) {
    $.ajax('/api/getRandomPlaylist/' + channelId,
        <JQueryAjaxSettings>{
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR: JQueryXHR, textStatus: string, errorThrown: string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data: any) {
                callback(Series.fromJSON(data))
            }
        })
}

function getQueue(series: string[], filters: string[], filterOptions: FilterOptions, callback: (contents: Content[]) => void) {
    $.ajax('/api/getQueue/' + channelId,
        <JQueryAjaxSettings>{
            type: 'POST',
            data: JSON.stringify({
                series: series,
                filters: filters,
                options: filterOptions.toJson()
            }),
            contentType: 'application/json',
            dataType: 'json',
            error: function (jqXHR: JQueryXHR, textStatus: string, errorThrown: string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data: any) {
                // console.log(data);
                callback(_.map(data, function (contentObj: any) {
                    return Content.fromJSON(contentObj);
                }));
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
    let filterOptions: FilterOptions = new FilterOptions(
        $("input[name='matchMethod']:checked").attr("value"),
        parseInt($("input#playlistSize").val())
    );
    getQueue(MediaController.getSelectedSeries(), MediaController.getSelectedFilters(), filterOptions, MediaController.loadQueue)
}

function getCookie(cname: string) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}