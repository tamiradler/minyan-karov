import { PsotUserOutput } from "./post-user-output";

export interface SignInIfc {
    userSignedIn(psotUserOutput:PsotUserOutput): void;
    userDisconnected(): void;
    getGoogleButton(): any;
}