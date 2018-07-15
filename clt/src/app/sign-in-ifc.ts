export interface SignInIfc {
    userSignedIn(): void;
    userDisconnected(): void;
    getGoogleButton(): any;
}