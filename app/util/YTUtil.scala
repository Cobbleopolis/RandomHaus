package util

import java.io.{FileInputStream, InputStream}

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.{PlaylistItem, PlaylistItemListResponse}
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
        val playlistItemRequest: YouTube#PlaylistItems#List = youtube.playlistItems().list("id,contentDetails,snippet")
            .setPlaylistId(playlistID)
            .setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo")

        var nextToken: String = ""

        do {
            playlistItemRequest.setPageToken(nextToken)
            val playlistItemResponse: PlaylistItemListResponse = playlistItemRequest.execute()

            playlistItems = playlistItems ++ playlistItemResponse.getItems.asScala

            nextToken = playlistItemResponse.getNextPageToken
        } while (nextToken != null)
        playlistItems
    }

}
