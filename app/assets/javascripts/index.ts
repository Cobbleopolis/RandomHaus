var channelId = 'UCboMX_UNgaPBsUOIgasn3-Q';

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
        })
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

function getQueue(callback: (contents: Content[]) => void) {
    $.ajax('/api/getQueue/' + channelId,
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
        })
}

function loadRandomVideo() {
    getRandomVideo(MediaController.loadContent)
}

function loadRandomPlaylist() {
    getRandomPlaylist(MediaController.loadPlaylist)
}

function loadQueue() {
    getQueue(MediaController.loadQueue)
}