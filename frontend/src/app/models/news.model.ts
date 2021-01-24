export class News {
    public id: number;
    public heading: string;
    public content: string;
    public culturalHeritageID: number;
    public adminID: number;
    public imageUri: string;
}

export class NewsRequest {
    public heading: string;
    public content: string;
    public culturalHeritageID: number;
    public adminID: number;
    public imageUri: string;
}