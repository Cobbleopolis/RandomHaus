class Content {
    constructor(public id:string, public channelId:string, public seriesId:string) {
    }

    static fromJSON(videoObj:any) {
        return new Content(videoObj.id, videoObj.channelId, videoObj.seriesId)
    }
}

class Series {
    constructor(public id:string, public channelId:string, public name:string, public publishedAt:Date) {
    }

    static fromJSON(playlistObj:any) {
        return new Series(playlistObj.id, playlistObj.channelId, playlistObj.name, playlistObj.publishedAt)
    }
}
