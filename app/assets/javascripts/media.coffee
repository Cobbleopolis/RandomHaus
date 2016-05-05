class @Video
    constructor: (@id, @channelId, @seriesId) ->

    @fromJSON: (videoObj) -> new @ videoObj.id, videoObj.channelId, videoObj.seriesId
        
class @Playlist
    constructor: (@id, @channelId, @name) ->
        
    @fromJSON: (playlistObj) -> new @ playlistObj.id, playlistObj.channelId, playlistObj.name