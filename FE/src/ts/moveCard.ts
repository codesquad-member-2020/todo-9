import fetchRequest from "./common/fetchRequest";
import updateDataset from "./updateDataset";
import Activity from "./activity";
import { METHOD } from "./common/constants";
import { SERVICE_URL, MOVE_URI } from "./common/configs";
import { REFRESH_MESSAGE } from "./common/confirmMessage";

class MoveCard {
  protected activity: Activity;
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

  dragEnterEventHandler({ target, offsetY }: DragEvent) {
    this.destCard = (<HTMLElement>target).closest(".card");
    this.destColumn = (<HTMLElement>target).closest(".column");
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

    this.renderCardCount();
    this.saveMoveCardInfo();
  }

  renderCardCount() {
    const startCount: HTMLElement | null = this.startColumn!.querySelector(".card-count");
    const endCount: HTMLElement | null | undefined = this.destColumn?.querySelector(".card-count");
    startCount!.innerText = (parseInt(startCount!.innerText) - 1).toString();
    endCount!.innerText = (parseInt(endCount!.innerText) + 1).toString();
  }

  saveMoveCardInfo() {
    const columnKey = this.draggingCard?.dataset.cardKey;
    const boardKey = this.startColumn!.dataset.columnKey;
    const body = {
      afterColumnKey: this.getAfterColumnKey(),
      afterBoardKey: this.destColumn?.dataset.columnKey,
    };

    if (boardKey === body.afterBoardKey) {
      body.afterColumnKey--;
    }

    this.requestMoveCard(boardKey!, columnKey!, body);
  }

  getAfterColumnKey() {
    if (!this.draggingCard?.nextElementSibling) {
      return "0";
    }

    const nextCard: Element = this.draggingCard?.nextElementSibling;
    const afterColumnKey: number = parseInt(nextCard.dataset.cardKey) + 1;

    return afterColumnKey;
  }

  requestMoveCard(boardKey: string, columnKey: string, body: any) {
    let requestURI: string = <string>(SERVICE_URL + MOVE_URI);
    const cvtURI = requestURI.replace("{boardKey}", boardKey).replace("{columnKey}", columnKey);
    console.log(cvtURI, body);

    fetchRequest(cvtURI, METHOD.PATCH, body)
      .then((response) => response.json())
      .then((data) => {
        updateDataset(data);
        this.activity.appendActivity(data);
      })
      .catch((error) => {
        alert(REFRESH_MESSAGE);
        console.error(error);
      });
  }
}

export { MoveCard };
