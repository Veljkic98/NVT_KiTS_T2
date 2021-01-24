export class Rating {
    public id: number;
    public grade: number;
    public chID: number;
    public userID: number;

    constructor(ratingCfg:RatingInterface) {
        this.id = ratingCfg.id;
        this.grade = ratingCfg.grade;
        this.chID = ratingCfg.chID;
        this.userID = ratingCfg.userID;
    }
}

interface RatingInterface{
    id?: number;
    grade: number;
    chID: number;
    userID?: number;
}
