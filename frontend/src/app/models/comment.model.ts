export class Comment {
    public id: number;
    public authenticatedUserID: number;
    public content: string;
    public imageUri: string;
    public userName: string;
    public culturaHeritageID: number;

    constructor(commentCfg: CommentInterface) {
        this.id = commentCfg.id;
        this.authenticatedUserID = commentCfg.authenticatedUserID;
        this.content = commentCfg.content;
        this.imageUri = commentCfg.imageUri;
        this.userName = commentCfg.userName;
        this.culturaHeritageID = commentCfg.culturaHeritageID;
    }
}

export interface CommentInterface {
    id?: number;
    authenticatedUserID?: number;
    content: string;
    imageUri?: string;
    userName?: string;
    culturaHeritageID: number;
}
