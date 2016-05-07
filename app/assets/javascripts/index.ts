var channelId: string = 'UCboMX_UNgaPBsUOIgasn3-Q';
var seriesFilters: JQuery = null;

$(function() {
    seriesFilters = $('#filterSeries').find('input[data-series-id]');
    seriesFilters.change(function(eventObject) {
        var seriesId = $(eventObject.target).attr('data-series-id');
        console.log(seriesId);
        if (seriesId === 'all') {
            $('#filterSeries').find('input[data-series-id!="all"]').prop('checked', false);
        } else {
            $('#filterSeries').find('input[data-series-id="all"]').prop('checked', false);
        }
    })
});

function onYouTubeIframeAPIReady() {
    getRandomVideo(MediaController.initPlayer)
}

function getRandomVideo(callback: (content: Content) => void) {
    $.ajax('/api/getRandomVideo/' + channelId,
        {
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
        {
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

function getQueue(filters: string[], callback: (contents: Content[]) => void) {
    $.ajax('/api/getQueue/' + channelId + "?filters=" + filters.join(","),
        {
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR: JQueryXHR, textStatus: string, errorThrown: string) {
                alert(errorThrown);
                callback(null);
            },
            success: function (data: any) {
                callback(_.map(data.queue, function(contentObj: any, i: number) {
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