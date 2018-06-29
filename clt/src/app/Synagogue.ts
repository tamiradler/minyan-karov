import { Minyan } from "./Minyan";

export class Synagogue {
	synagogueId: string;
	synagogueName: string;
	address: string;
	coordinate: string;
	nosach: string;
	minyans: Minyan[] = [];
}