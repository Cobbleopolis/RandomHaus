class @MediaController
    @player: null

    @initPlayer: (video) ->
        @player = new YT.Player 'player',
            height: 390
            width: 640
            videoId: video.id
            events:
                'onReady': onPlayerReady
                'onStateChange': onPlayerStateChange
                
    @loadVideo: (video) ->
        @player.loadVideoById(video.id)
        $('#vidID').text(video.id)

    @loadPlaylist: (playlist) ->
        @player.loadPlaylist(
          list: playlist.id
          listType: 'playlist'
        )
        $('#vidID').text(playlist.id)

    @loadQueue: (array) ->
        @player.loadPlaylist(array.map (video) -> video.id)
        $('#vidID').text('')

    onPlayerReady = (event) ->
#        event.target.playVideo()

    onPlayerStateChange = (event) ->
        if(event.data == YT.PlayerState.ENDED)
            loadRandomVideo()