import { Minyan } from "./Minyan";

export class Synagogue {
	synagogueId: string;
	synagogueName: string;
	address: string;
	coordinate: string;
	nosach: string = 'nosach_ahid';
	minyans: Minyan[] = [];
}