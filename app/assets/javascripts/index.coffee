player = null

window.onYouTubeIframeAPIReady = () ->
    getRandomVideo((videoId) ->
        player = new YT.Player 'player',
            height: 390
            width: 640
            videoId: videoId
            events:
                'onReady': onPlayerReady
        $('#vidID').text(videoId)
    )


getRandomVideo = (callback) ->
    $.ajax "/api/getRandomVideo",
        type: "GET"
        dataType: "text"
        error: (jqXHR, textStatus, errorThrown) ->
            alert(errorThrown)
            callback('')
        success: (data, textStatus, jqXHR) ->
            callback(data)
            
onPlayerReady = (event) ->
#    event.target.playVideo()