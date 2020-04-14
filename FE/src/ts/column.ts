import "../style/style.css";

import IView from "./view";
import Activity from "./Activity";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import {
  getColumnWrap,
  getColumnHeader,
  getCardInput,
  getCardContents,
  cardTemplate,
} from "./columnTemplate";
import { qs$, qsAll$, addClass, removeClass } from "./lib/util";
import { DELETE_MESSAGE } from "./common/confirmMessage";
import fetchRequest from "./common/fetchRequest";
import {
  configs,
  SERVICE_URL,
  INIT_DATA_URI,
  EDIT_DATA_URI,
  DELETE_DATA_URI,
} from "./common/configs";
import { METHOD } from "./common/constants";
import TodoDataModel from "./tododatamodel";
import { mouseDownHandler } from "./drag";

class Column implements IView {
  private activity: Activity;
  private editNote: EditNote;
  private editColumn: EditColumn;
  private placeHolderVisible: boolean;
  private inputValue: string;
  private todoDataModel: TodoDataModel = TodoDataModel.getInstance();
  private dragged: Node | null;

  constructor(activity: Activity, editNote: EditNote, editColumn: EditColumn) {
    this.activity = activity;
    this.editNote = editNote;
    this.editColumn = editColumn;
    this.placeHolderVisible = false;
    this.inputValue = "";
    this.dragged = null;
  }

  render(): string {
    return getColumnWrap();
  }

  registerEventListener(): void {
    qs$(".column-wrap").addEventListener("click", ({ target }: Event) => {
      const clickColumn: any = (<HTMLInputElement>target).closest(".column");

      if ((<HTMLInputElement>target).className === "add") {
        this.plusBtnClickEventHandler(clickColumn);
      } else if ((<HTMLInputElement>target).className.includes("add-btn")) {
        this.cardAddBtnClickEventHandler(clickColumn);
      } else if ((<HTMLInputElement>target).className.includes("cancel-btn")) {
        this.plusBtnClickEventHandler(clickColumn);
      }
    });

    qs$(".column-wrap").addEventListener("mousedown", (evt: Event) => {
      if (evt.target!.className === "card") {
        mouseDownHandler(evt);
      }
    });

    document.body.addEventListener(
      "click",
      (evt: Event) => {
        const className = (<HTMLInputElement>evt.target).className;

        if (className === "close card-close") {
          this.cardDeleteClickEventHandler(evt);
        }
      },
      false
    );

    document.body.addEventListener("dblclick", (evt: Event) => {
      const className = (<HTMLInputElement>evt.target).className;

      if (className === "card") {
        const contentWrap = <HTMLInputElement>evt.target;
        const cardElement: any = contentWrap.closest(".card");
        this.cardDoubleClickEventHandler(cardElement);
      }
    });

    qs$(".column-wrap").addEventListener("input", (evt: Event) => {
      this.inputEventHandler(evt);
    });
  }

  showConfirm(message: string): boolean {
    const result = confirm(message);
    return result;
  }

  requestDeleteCard(card: HTMLElement) {
    const ids = (<string>card.id).split("-");
    const boardId = parseInt(ids[0].substr(1));
    const columnId = parseInt(ids[1]);

    let requestURI: string = <string>(SERVICE_URL + DELETE_DATA_URI);
    const cvtURI = requestURI
      .replace("{boardKey}", this.todoDataModel.getColumnBoardKey(boardId))
      .replace("{columnKey}", this.todoDataModel.getCardColumnKey(boardId, columnId));

    fetchRequest(cvtURI, METHOD.DELETE)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      });
  }

  cardDeleteClickEventHandler(evt: Event) {
    const result = this.showConfirm(DELETE_MESSAGE);

    if (result) {
      const target = <HTMLInputElement>evt.target;
      const cardWrap: any = target.closest(".card");
      const column: any = target.closest(".card-list-wrap");
      column.removeChild(cardWrap);

      const card = <HTMLElement>target.closest(".card");

      this.requestDeleteCard(card);
    }
  }

  cardDoubleClickEventHandler(card: HTMLInputElement) {
    const contentElement: any = card.querySelector(".card-content");
    const content = contentElement.innerHTML;
    const cardId = card.id;
    const columnId: any = card.closest(".column")?.id;

    this.editNote.showModal(columnId, cardId, content, contentElement);
  }

  cardAddBtnClickEventHandler(clickColumn: any) {
    const cardList: any = clickColumn.querySelector(".card-list-wrap");
    const input: any = clickColumn.querySelector("#card-input");
    const cardCount: any = clickColumn.querySelector(".card-count");
    const cardId: string = (parseInt(cardCount.innerText) + 1).toString();
    const cardKey: string = "";

    cardList?.insertAdjacentHTML("afterbegin", cardTemplate(cardId, cardKey, this.inputValue));
    cardCount.innerText = cardId;

    input.value = null;
    input.focus();

    this.inputValue = "";
    clickColumn.querySelector(".add-btn").disabled = true;
  }

  inputEventHandler({ target }: Event) {
    const addBtn: any = (<HTMLInputElement>target).closest(".column")?.querySelector(".add-btn");
    this.inputValue = (<HTMLInputElement>target).value;

    if (this.inputValue === "") {
      addBtn.disabled = true;
      return;
    }
    addBtn.disabled = false;
  }

  plusBtnClickEventHandler(column: HTMLInputElement | null): void {
    const cardInput = column?.querySelector(".input-wrap");
    const input: any = column?.querySelector("#card-input");

    if (!this.getPlaceHolderVisible(cardInput)) {
      addClass(cardInput, "hidden");
      this.setPlaceholderVisible(true);
      input.value = null;

      return;
    }
    removeClass(cardInput, "hidden");
    this.setPlaceholderVisible(false);
    input.focus();
  }

  setPlaceholderVisible(visible: boolean): void {
    this.placeHolderVisible = visible;
  }

  getPlaceHolderVisible(cardInput: Element | null | undefined): boolean {
    if (!cardInput) return false;
    return cardInput.classList.contains("hidden");
  }

  receiveInitialData({ columns }: any): void {
    let columnTemplate: string = "";

    columns.forEach((element: Element) => {
      const { id, name, boardKey, cards }: any = element;
      columnTemplate += `
      <div class="project-columns">
        <div class="column" data-column-id="${id}" data-column-key="${boardKey}">
          ${getColumnHeader(name, cards)}
          <div class="card-wrap">
            ${getCardInput()}
            <div class="card-list-wrap">
              ${getCardContents(cards)}
            </div>
          </div>
        </div>
      </div>
      `;
    });
    qs$(".column-wrap").innerHTML = columnTemplate;
  }
}

export default Column;
