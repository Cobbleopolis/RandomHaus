class Content {
    constructor(public id:string, public channelId:string, public seriesId:string) {
    }

    static fromJSON(videoObj:any) {
        return new Content(videoObj.id, videoObj.channelId, videoObj.seriesId)
    }
    
    toJson(): any {
        return {
            "id": this.id,
            "channelId": this.channelId,
            "seriesIs": this.seriesId
        }
    }
}

class Series {
    constructor(public id:string, public channelId:string, public name:string, public publishedAt:Date) {
    }

    static fromJSON(playlistObj:any) {
        return new Series(playlistObj.id, playlistObj.channelId, playlistObj.name, playlistObj.publishedAt)
    }
}

class FilterOptions {
    matchMethod:string;
    videoCount:number;
    constructor(matchMethod:string, videoCount:number) {
        this.matchMethod = matchMethod;
        this.videoCount = videoCount;
    }

    toJson():any {
        return {
            "matchMethod": this.matchMethod,
            "videoCount": this.videoCount
        }
    }
}
