export class Comment {
    public id: number;
    public authenticatedUserID: number;
    public content : string;
    public imageUri : string;
    public userName : string;
  
    constructor(id: number, authenticatedUserID: number, content : string, imageUri : string, userName: string) {
      this.id = id;
      this.authenticatedUserID = authenticatedUserID;
      this.content = content;
      this. userName = userName;
      this.imageUri = imageUri;
    }
  }