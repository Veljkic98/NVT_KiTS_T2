export class News {
    public id: number;
    public heading: string;
    public content: string;
    public culturalHeritageID: number;
    public adminID: number;
    public imageUri: string;

    constructor(id?: number, heading?: string, content?: string, culturalHeritageID?: number, adminID?: number, imageUri?: string) {
        this.id = id;
        this.heading = heading;
        this.content = content;
        this.culturalHeritageID = culturalHeritageID;
        this.adminID = adminID;
        this.imageUri = imageUri;
    }
}
