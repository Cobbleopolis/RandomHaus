channelId = 'UCboMX_UNgaPBsUOIgasn3-Q'

window.onYouTubeIframeAPIReady = () ->
    getRandomVideo((videoObj) ->
        MediaController.initPlayer(Video.fromJSON(videoObj))
    )


getRandomVideo = (callback) ->
    $.ajax '/api/getRandomVideo/' + channelId,
        type: 'GET'
        dataType: 'json'
        error: (jqXHR, textStatus, errorThrown) ->
            alert(errorThrown)
            callback('')
        success: (data, textStatus, jqXHR) ->
            callback(data)

getRandomPlaylist = (callback) ->
    $.ajax '/api/getRandomPlaylist/' + channelId,
        type: 'GET'
        dataType: 'json'
        error: (jqXHR, textStatus, errorThrown) ->
            alert(errorThrown)
            callback('')
        success: (data, textStatus, jqXHR) ->
            callback(data)

getQueue = (callback) ->
    $.ajax '/api/getQueue/' + channelId,
        type: 'GET'
        dataType: 'json'
        error: (jqXHR, textStatus, errorThrown) ->
            alert(errorThrown)
            callback('')
        success: (data, textStatus, jqXHR) ->
            callback(data)
            
window.loadRandomVideo = () ->
    getRandomVideo((videoObj) ->
        MediaController.loadVideo(Video.fromJSON(videoObj))
    )
    
window.loadRandomPlaylist = () ->
    getRandomPlaylist((playlistObj) ->
        MediaController.loadPlaylist(Playlist.fromJSON(playlistObj))
    )
    
window.getQueue = () ->
    getQueue((array) ->
        MediaController.loadQueue(array.queue.map (videoObj) -> Video.fromJSON(videoObj))
    )