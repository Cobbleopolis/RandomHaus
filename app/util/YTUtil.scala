package util

import java.io.{FileInputStream, InputStream}

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.{Playlist, PlaylistItem, PlaylistItemListResponse, PlaylistListResponse}
import reference.Reference

import scala.collection.JavaConverters._

object YTUtil {

    val net: NetHttpTransport = new NetHttpTransport()

    val jack = JacksonFactory.getDefaultInstance

    val loginStream: InputStream = new FileInputStream("conf/client.json")

    val scopes: List[String] = List[String]("https://www.googleapis.com/auth/youtube")

    val cred: GoogleCredential = GoogleCredential.fromStream(loginStream, net, jack).createScoped(scopes.asJavaCollection)

    val youtube: YouTube = new YouTube.Builder(net, jack, cred).setApplicationName(Reference.APPLICATION_NAME).build()

    def getPlaylistItems(playlistID: String): List[PlaylistItem] = {
        var playlistItems: List[PlaylistItem] = List[PlaylistItem]()
        val playlistItemRequest: YouTube#PlaylistItems#List = youtube.playlistItems().list("id,contentDetails,snippet,status")
            .setPlaylistId(playlistID)
            .setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt,status),nextPageToken,pageInfo")

        var nextToken: String = ""

        do {
            playlistItemRequest.setPageToken(nextToken)
            val playlistItemResponse: PlaylistItemListResponse = playlistItemRequest.execute()

            playlistItems = playlistItems ++ playlistItemResponse.getItems.asScala.filter(playlistItem => playlistItem.getStatus.getPrivacyStatus == "public")

            nextToken = playlistItemResponse.getNextPageToken
        } while (nextToken != null)
        playlistItems
    }

    def getVideoTags(videoId: String): List[String] = {
        val videos = youtube.videos().list("snippet").setId(videoId).execute().getItems.asScala
        if (videos.nonEmpty)
            if (videos.head.getSnippet.getTags != null)
                videos.head.getSnippet.getTags.asScala.toList
            else
                List()
        else
            List()
    }

    def getAllPlaylistsFromUser(channelId: String): List[Playlist] = {
        var playlists: List[Playlist] = List[Playlist]()
        val playlistRequest: YouTube#Playlists#List = youtube.playlists.list("id,snippet,status")
            .setChannelId(channelId)
        var nextToken: String = ""

        while (nextToken != null) {
            playlistRequest.setPageToken(nextToken)
            val playlistResponse: PlaylistListResponse = playlistRequest.execute()

            playlists = playlists ++ playlistResponse.getItems.asScala.filter(playlist => playlist.getStatus.getPrivacyStatus == "public")

            nextToken = playlistResponse.getNextPageToken
        }
        playlists
    }

}
