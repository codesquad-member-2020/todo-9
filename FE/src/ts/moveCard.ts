import fetchRequest from "./common/fetchRequest";
import Activity from "./activity";
import { METHOD } from "./common/constants";
import { SERVICE_URL, MOVE_URI } from "./common/configs";

class MoveCard {
  private activity: Activity;
  private draggingCard: HTMLElement | null;
  private destCard: HTMLElement | null;
  private startColumn: HTMLElement | null;
  private destColumn: HTMLElement | null;

  constructor(activity: Activity) {
    this.activity = activity;
    this.draggingCard = null;
    this.destCard = null;
    this.startColumn = null;
    this.destColumn = null;
  }

  isAbove(draggingCard: HTMLElement, destHeight: number) {
    const half = draggingCard.offsetHeight / 2;
    console.log(half, destHeight);
    return half > destHeight;
  }

  dragStartEventHandler({ target }: Event) {
    this.draggingCard = <HTMLElement>target;
    this.draggingCard.style.opacity = "0.5";
    this.startColumn = this.draggingCard.closest(".column");
  }

  dragOverEventHandler(evt: Event) {
    evt.preventDefault();
  }

  dragEnterEventHandler({ toElement, offsetY }: Event) {
    this.destCard = toElement.closest(".card");
    this.destColumn = toElement.closest(".column");
    this.draggingCard!.classList.add("placeholder");

    if (this.destCard && this.isAbove(this.draggingCard!, offsetY)) {
      this.destCard.after(this.draggingCard!);
    } else if (this.destCard && !this.isAbove(this.draggingCard!, offsetY)) {
      this.destCard.before(this.draggingCard!);
    } else if (this.destColumn) {
      this.destColumn!.querySelector(".card-list-wrap")?.appendChild(this.draggingCard!);
    }
  }

  dragEndEventHandler() {
    this.draggingCard!.style.opacity = "";
    this.draggingCard!.classList.remove("placeholder");
    this.saveMoveCardInfo();
  }

  saveMoveCardInfo() {
    const columnKey = this.draggingCard?.dataset.cardKey;
    const boardKey = this.startColumn!.dataset.columnKey;
    const body = {
      afterColumnKey: this.destCard?.dataset.cardKey,
      afterBoardKey: this.destColumn?.dataset.columnKey,
    };

    this.requestMoveCard(boardKey!, columnKey!, body);
  }

  requestMoveCard(boardKey: string, columnKey: string, body: any) {
    let requestURI: string = <string>(SERVICE_URL + MOVE_URI);
    const cvtURI = requestURI.replace("{boardKey}", boardKey).replace("{columnKey}", columnKey);

    fetchRequest(cvtURI, METHOD.PATCH, body)
      .then((response) => response.json())
      .then((data) => {
        this.activity.appendActivity(data);
      });
  }
}

export { MoveCard };
