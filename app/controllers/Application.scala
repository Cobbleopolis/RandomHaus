package controllers

import java.io.{FileInputStream, InputStream}

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model._
import com.google.inject.Inject
import play.api.mvc._
import services.ServiceYouTubeData

import scala.collection.JavaConverters._

class Application @Inject() (youTube: ServiceYouTubeData) extends Controller {

    val net: NetHttpTransport = new NetHttpTransport()

    val jack = JacksonFactory.getDefaultInstance

    val loginStream: InputStream = new FileInputStream("conf/client.json")

    val scopes: List[String] = List[String]("https://www.googleapis.com/auth/youtube")

    val cred: GoogleCredential = GoogleCredential.fromStream(loginStream, net, jack).createScoped(scopes.asJavaCollection)

    val yt: YouTube = new YouTube.Builder(net, jack, cred).setApplicationName("randomhaus").build()

    val defaultPlaylistID: String = "PLbIc1971kgPDJjVdQcLZZz4Exygpo2cuB"

    def index = Action {
        var playlistItems: List[PlaylistItem] = List[PlaylistItem]()
        val playlistItemRequest: YouTube#PlaylistItems#List = yt.playlistItems().list("id,contentDetails,snippet")
            .setPlaylistId(defaultPlaylistID)
            .setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo")

        var nextToken: String = ""

        do {
            playlistItemRequest.setPageToken(nextToken)
            val playlistItemResponse: PlaylistItemListResponse = playlistItemRequest.execute()

            playlistItems = playlistItems ++ playlistItemResponse.getItems.asScala

            nextToken = playlistItemResponse.getNextPageToken
        } while (nextToken != null)
        val playlist: PlaylistSnippet = yt.playlists().list("snippet").setId(defaultPlaylistID).execute().getItems.get(0).getSnippet
        Ok(views.html.index("Your new application is ready.", playlist, playlistItems))
    }

    def list(playlistID: String) = Action {
        var playlistItems: List[PlaylistItem] = List[PlaylistItem]()
        val playlistItemRequest: YouTube#PlaylistItems#List = yt.playlistItems().list("id,contentDetails,snippet")
            .setPlaylistId(playlistID)
            .setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo")

        var nextToken: String = ""

        do {
            playlistItemRequest.setPageToken(nextToken)
            val playlistItemResponse: PlaylistItemListResponse = playlistItemRequest.execute()

            playlistItems = playlistItems ++ playlistItemResponse.getItems.asScala

            nextToken = playlistItemResponse.getNextPageToken
        } while (nextToken != null)
        val playlist: PlaylistSnippet = yt.playlists().list("snippet").setId(playlistID).execute().getItems.get(0).getSnippet
        Ok(views.html.index("Your new application is ready.", playlist, playlistItems))
    }

    def channel(channelID: String) = Action {
        //        val playlistListResponse: PlaylistListResponse = yt.playlists().list("contentDetails").setChannelId(channelID).execute()
        val playlistListRequest: YouTube#Playlists#List = yt.playlists().list("snippet,contentDetails")
            .setChannelId(channelID)

        var playlists: Array[(String, Int)] = Array[(String, Int)]()

        var totalCount: Int = 0

        var nextToken: String = ""

        do {
            playlistListRequest.setPageToken(nextToken)
            val res: PlaylistListResponse = playlistListRequest.execute()

            playlists = playlists ++ res.getItems.asScala.map( playlist => {
                totalCount += playlist.getContentDetails.getItemCount.toInt
                (playlist.getSnippet.getTitle, playlist.getContentDetails.getItemCount.toInt)
            })

            nextToken = res.getNextPageToken
        } while (nextToken != null)
        val channelName: String = yt.channels().list("snippet").setId(channelID).execute().getItems.get(0).getSnippet.getTitle
        Ok(views.html.channel(channelName, playlists, totalCount))
    }

}