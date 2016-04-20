package controllers

import java.io.{FileInputStream, InputStream}

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.{PlaylistItem, PlaylistItemListResponse, PlaylistListResponse, PlaylistSnippet}
import play.api.mvc._

import scala.collection.JavaConverters._

class Application extends Controller {

	val net: NetHttpTransport = new NetHttpTransport()

	val jack = JacksonFactory.getDefaultInstance

	val loginStream: InputStream = new FileInputStream("conf/client.json")

	val scopes: List[String] = List[String]("https://www.googleapis.com/auth/youtube")

	val cred: GoogleCredential = GoogleCredential.fromStream(loginStream, net, jack).createScoped(scopes.asJavaCollection)

	val yt: YouTube = new YouTube.Builder(net, jack, cred).setApplicationName("randomhaus").build()

	val playlistID: String = "PLbIc1971kgPDqLZExuoPHbMMdDIB_ok3b"

	def index = Action {
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

}