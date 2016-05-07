module MediaController {
    import EventArgs = YT.EventArgs;
    var player: YT.Player = null;

    export function initPlayer(content: Content) {
        player = new YT.Player('player', {
            height: 390,
            width: 640,
            videoId: content.id,
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
        setIdDiv(content.id);
    }

    export function loadContent(content: Content) {
        player.loadVideoById(content.id);
        setIdDiv(content.id)
    }

    export function loadPlaylist(playlist: Series) {
        player.loadPlaylist({
            list: playlist.id,
            listType: 'playlist'
        });
        setIdDiv(playlist.id)
    }

    export function loadQueue(contents: Content[]) {
        player.loadPlaylist(_.map(contents, function(v: Content) {
            return v.id
        }));
        setIdDiv('Custom Playlist');
    }

    function onPlayerReady(event: EventArgs) {

    }

    function onPlayerStateChange(event: EventArgs) {
        if (event.data == YT.PlayerState.ENDED) {}
    }

    function setIdDiv(text: string) {
        $('#vidID').text(text)
    }

    export function getSelectedFilters(): string[] {
        var filters: string[] = [];
        _(seriesFilters).filter(function(input){
            return input.checked;
        }).forEach(function(input) {
            filters[filters.length] = $(input).attr('data-series-id');
        });
        return filters;
    }
}
