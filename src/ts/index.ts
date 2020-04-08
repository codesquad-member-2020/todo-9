//import '../style/reset.css';
import '../style/style.css';

window.addEventListener('DOMContentLoaded', (event) => {
    dispatcher.on
});

/* dispatcher.ts */

class Dispatcher {    
    on<
        MessageType extends keyof AppMessageMap
    >(
        messageType: MessageType,
        handler: (message: AppMessageMap[MessageType] & {messageType: MessageType}) => void
    ): void { }
}

/* messages.ts */
interface AddCommentMessage {
    commentId: number;
    comment: string;
    userId: number;
}

interface PostPictureMessage {
    pictureId: number;
    userId: number;
}

interface AppMessageMap {
    "ADD_COMMENT": AddCommentMessage,
    "POST_PICTURE": PostPictureMessage
}
type AppMessage = AppMessageMap[keyof AppMessageMap];

/* app.ts */
const dispatcher = new Dispatcher();


dispatcher.on("ADD_COMMENT", (message) => {
    console.log(message.comment);
});