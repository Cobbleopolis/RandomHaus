/// <reference path="models.ts"/>
/// <reference path="index.ts"/>

module MediaController {
    import EventArgs = YT.EventArgs;
    var player:YT.Player = null;
    var main:JQuery = null;

    export function initPlayer(content:Content) {
        main = $("#main");
        var width = main.width() - parseInt(main.css('padding-right').replace('px', ''));
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
    }

    export function loadPlaylist(playlist:Series) {
        player.loadPlaylist({
            list: playlist.id,
            listType: 'playlist'
        });
        // setIdDiv(playlist.id)
    }

    export function loadQueue(contents:Content[]) {
        if(contents.length > 0)
            player.loadPlaylist(_.map(contents, function (v:Content) {
                return v.id
            }));
        else
            alert("No videos found with those parameters");
        // setIdDiv('Custom Playlist');
    }

    function onPlayerReady(event:EventArgs) {

    }

    function onPlayerStateChange(event:EventArgs) {
        if (event.data == YT.PlayerState.ENDED) {
        }
    }

    function setIdDiv(text:string) {
        $("#vidID").text(text)
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
}
