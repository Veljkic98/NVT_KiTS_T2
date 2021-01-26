export class User {
    public id: number;
    public firstName: string;
    public lastName: string;
    public email: string;
    public approved: boolean;

constructor(userCfg: UserInterface) {
    this.id = userCfg.id;
    this.firstName = userCfg.firstName;
    this.lastName = userCfg.lastName;
    this.email = userCfg.email;
    this.approved = userCfg.approved;
}
}

interface UserInterface{
	id?: number;
    firstName: string;
    lastName: string;
    email: string;
    approved?: boolean;
}
