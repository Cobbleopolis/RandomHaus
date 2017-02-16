/// <reference path="models.ts"/>
/// <reference path="index.ts"/>

module MediaController {
    import EventArgs = YT.EventArgs;
    export var player:YT.Player = null;
    var main:JQuery = null;
    export var currentPlaylist: Content[] = [];

    export function initPlayer(content:Content) {
        main = $("#main");
        var width = main.width();
        player = new YT.Player('player', {
            height: width * (9 / 16),
            width: width,
            videoId: content.id,
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
        // setIdDiv(content.id);
    }

    export function loadContent(content:Content) {
        player.loadVideoById(content.id);
        // setIdDiv(content.id)
        MediaController.onMediaLoad();
    }

    export function loadPlaylist(playlist:Series) {
        player.loadPlaylist({
            list: playlist.id,
            listType: 'playlist'
        });
        // setIdDiv(playlist.id)
        MediaController.onMediaLoad();
    }

    export function loadQueue(contents:Content[]) {
        currentPlaylist = contents;
        if(contents.length > 0)
            player.loadPlaylist(_.map(contents, function (v:Content) {
                return v.id
            }));
        else
            alert("No videos found with those parameters");
        // setIdDiv('Custom Playlist');
        MediaController.onMediaLoad();
    }

    export function onMediaLoad() {
        window.scrollTo(0, 0);
    }

    function onPlayerReady(event:EventArgs) {
        player.setSize(main.width(), main.width() * (9 / 16));
    }

    function onPlayerStateChange(event:EventArgs) {
        if (event.data == YT.PlayerState.ENDED) {
        }
    }

    export function getSelectedSeries():string[] {
        var series:string[] = [];
        _(seriesFilters).filter(function (input:any) {
            return input.checked && $(input).attr('data-series-id') !== 'all';
        }).forEach(function (input) {
            series[series.length] = $(input).attr('data-series-id');
        });
        return series;
    }
    
    export function getSelectedFilters():string[] {
        var filters:string[] = [];
        _(filterGroups.find('input')).filter(function (input:any) {
            return input.checked && $(input).attr('data-tag') !== 'all';
        }).forEach(function (input) {
            filters[filters.length] = $(input).attr('data-tag');
        });
        return filters;
    }

    export function saveGeneratedPlaylist():void {
        if(localStorage)
            if (currentPlaylist.length > 0) {
                localStorage.setItem('savedPlaylist' + channelId, JSON.stringify(_.map(currentPlaylist, function (c:Content) {
                    return c.toJson();
                })));
                alert("Playlist Saved");
            } else
                alert("Please generate a playlist before trying to save one.");
        else
            alert("It looks like your browser doesn't support local storage. Sorry.")
    }

    export function loadGeneratedPlaylist():void {
        if(localStorage){
            var playlistString =localStorage.getItem('savedPlaylist' + channelId);
            if (playlistString)
                loadQueue(_.map(JSON.parse(playlistString), Content.fromJSON));
            else
                alert("You do not have a playlist saved for the current channel.");
        } else
            alert("It looks like your browser doesn't support local storage. Sorry.")
    }
}
