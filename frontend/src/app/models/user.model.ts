export class User {
    public id: number;
    public firstName: string;
    public lastName: string;
    public email: string;

constructor(userCfg:UserInterface) {
    this.id = userCfg.id;
    this.firstName = userCfg.firstName;
    this.lastName = userCfg.lastName;
    this.email = userCfg.email;
}
}

interface UserInterface{
	id?: number;
    firstName: string;
    lastName: string;
    email: string;
}
