/// <reference path="models.ts"/>
/// <reference path="index.ts"/>

module MediaController {
    import EventArgs = YT.EventArgs;
    export let player: YT.Player = null;
    let main: JQuery = null;
    export let currentPlaylist: Content[] = [];

    export function initPlayer(content: Content) {
        main = $('#main');
        let width = main.width();
        player = new YT.Player('player', {
            height: width * (9 / 16),
            width: width,
            videoId: content.id,
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
        loadSavedPlaylistOptions();
        // setIdDiv(content.id);
    }

    export function loadContent(content: Content) {
        player.loadVideoById(content.id);
        // setIdDiv(content.id)
        MediaController.onMediaLoad();
    }

    export function loadPlaylist(playlist: Series) {
        player.loadPlaylist({
            list: playlist.id,
            listType: 'playlist'
        });
        // setIdDiv(playlist.id)
        MediaController.onMediaLoad();
    }

    export function loadQueue(contents: Content[]) {
        currentPlaylist = contents;
        if (contents.length > 0)
            player.loadPlaylist(_.map(contents, function (v: Content) {
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

    function onPlayerReady(event: EventArgs) {
        player.setSize(main.width(), main.width() * (9 / 16));
    }

    function onPlayerStateChange(event: EventArgs) {
        if (event.data == YT.PlayerState.ENDED) {
        }
    }

    export function getSelectedSeries(): string[] {
        let series: string[] = [];
        _(seriesFilters).filter(function (input: any) {
            return input.checked && $(input).attr('data-series-id') !== 'all';
        }).forEach(function (input) {
            series[series.length] = $(input).attr('data-series-id');
        });
        return series;
    }

    export function getSelectedFilters(): string[] {
        let filters: string[] = [];
        _(filterGroups.find('input')).filter(function (input: any) {
            return input.checked && $(input).attr('data-tag') !== 'all';
        }).forEach(function (input) {
            filters[filters.length] = $(input).attr('data-tag');
        });
        return filters;
    }

    export function saveGeneratedPlaylist(playlistName: string): void {
        if (localStorage) {
            try {
                let channelPlaylists = JSON.parse(localStorage.getItem('savedPlaylists.' + channelId));
                if (!channelPlaylists)
                    channelPlaylists = {};
                if (currentPlaylist.length > 0) {
                    channelPlaylists[playlistName] = currentPlaylist;
                    localStorage.setItem('savedPlaylists.' + channelId, JSON.stringify(channelPlaylists));
                    bootAlert(savePlaylistName, 'success', 'Playlist Saved');
                    loadSavedPlaylistOptions()
                } else
                    bootAlert(savePlaylistName, 'danger', 'Please generate a playlist before trying to save one.');
            } catch (e) {
                bootAlert(savePlaylistName, 'danger', 'There was an error loading the playlist "' + playlistName + '".');
                console.error(e);
            }
        } else
            bootAlert(savePlaylistName, 'danger', 'It looks like your browser doesn\'t support local storage. Sorry.');
    }

    export function loadGeneratedPlaylist(playlistName: string): void {
        if (localStorage) {
            try {
                let channelPlaylists = JSON.parse(localStorage.getItem('savedPlaylists.' + channelId));
                if (!channelPlaylists)
                    bootAlert(loadPlaylistSelect, 'danger', 'You have no saved playlists for this channel.');
                else {
                    loadQueue(channelPlaylists[playlistName]);
                    loadPlaylistModal.modal('hide')
                }
            } catch(e) {
                bootAlert(loadPlaylistSelect, 'danger', 'There was an error loading the playlist "' + playlistName + '".');
                console.error(e);
            }
        } else
            bootAlert(loadPlaylistSelect, 'danger', 'It looks like your browser doesn\'t support local storage. Sorry.');
    }

    export function deleteGeneratedPlaylists(): void {
        if (localStorage) {
            try {
                let channelPlaylists = JSON.parse(localStorage.getItem('savedPlaylists.' + channelId));
                if (!channelPlaylists)
                    bootAlert(loadPlaylistSelect.next(), 'danger', 'You have no saved playlists for this channel.');
                else {
                    deletePlaylistSelect.val().forEach((playlistName: string) => {
                        delete channelPlaylists[playlistName]
                    });
                    localStorage.setItem('savedPlaylists.' + channelId, JSON.stringify(channelPlaylists));
                    loadSavedPlaylistOptions();
                    bootAlert(deletePlaylistSelect.next(), 'success', 'Playlists Deleted')
                }
            } catch (e) {
                bootAlert(deletePlaylistSelect.next(), 'danger', 'There was an error deleting the playlist(s).');
                console.error(e);
            }
        } else
            bootAlert(deletePlaylistSelect.next(), 'danger', 'It looks like your browser doesn\'t support local storage. Sorry.');
    }

    export function loadSavedPlaylistOptions() {
        if (localStorage) {
            let channelPlaylists = JSON.parse(localStorage.getItem('savedPlaylists.' + channelId));
            if (!channelPlaylists)
                channelPlaylists = {};
            let optionStrings = '';
            Object.keys(channelPlaylists).forEach(function (playlistName) {
                optionStrings += '<option>' + playlistName + '</option>';
            });
            loadPlaylistSelect.html(optionStrings);
            deletePlaylistSelect.html(optionStrings)
        }
    }
}
