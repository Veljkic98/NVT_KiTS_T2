export class Comment {
    public id: number;
    public authenticatedUserID: number;
    public content: string;
    public imageUri: string;
    public userName: string;

    constructor(commentCfg: CommentInterface) {
        this.id = commentCfg.id;
        this.authenticatedUserID = commentCfg.authenticatedUserID;
        this.content = commentCfg.content;
        this.imageUri = commentCfg.imageUri;
        this.userName = commentCfg.userName;
    }
}

export interface CommentInterface {
    id? :number;
    authenticatedUserID: number;
    content: string;
    imageUri: string;
    userName: string;
}
 