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
import { SERVICE_URL, ADD_URI, DELETE_DATA_URI } from "./common/configs";
import { METHOD } from "./common/constants";
import { MoveCard } from "./moveCard";

class Column extends MoveCard implements IView {
  private activity: Activity;
  private editNote: EditNote;
  private editColumn: EditColumn;
  private placeHolderVisible: boolean;
  private inputValue: string;

  constructor(activity: Activity, editNote: EditNote, editColumn: EditColumn) {
    super();
    this.activity = activity;
    this.editNote = editNote;
    this.editColumn = editColumn;
    this.placeHolderVisible = false;
    this.inputValue = "";
  }

  render(): string {
    return getColumnWrap();
  }

  registerEventListener = (): void => {
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

    qs$(".menu").addEventListener("click", (evt: Event) =>{
      this.menuBtnClickEventHandler(evt);
    });

    document.addEventListener("dragstart", (evt: Event) => {
      if ((<HTMLInputElement>evt.target).className === "card") {
        this.dragStartEventHandler(evt);
      }
    });
    document.addEventListener("dragover", (evt: Event) => this.dragOverEventHandler(evt));
    document.addEventListener("dragenter", (evt: Event) => this.dragEnterEventHandler(evt));
    document.addEventListener("dragend", (evt: Event) => this.dragEndEventHandler(evt));

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
  };

  showConfirm(message: string): boolean {
    const result = confirm(message);
    return result;
  }

  requestDeleteCard(boardKey: string, columnKey: string) {
    let requestURI: string = <string>(SERVICE_URL + DELETE_DATA_URI);
    const cvtURI = requestURI.replace("{boardKey}", boardKey).replace("{columnKey}", columnKey);

    fetchRequest(cvtURI, METHOD.DELETE)
      .then((response) => response.json())
      .then((data) => {
        this.activity.appendActivity(data);
      });
  }

  private menuBtnClickEventHandler(evt: Event) {
    this.activity.showActivity();
  }

  private cardDeleteClickEventHandler(evt: Event) {
    const result = this.showConfirm(DELETE_MESSAGE);

    if (result) {
      const target = <HTMLInputElement>evt.target;
      const card: Element = <Element>target.closest(".card");
      const cardWrap: Element = <Element>target.closest(".card-list-wrap");
      const column: Element = <Element>target.closest(".column");
      cardWrap.removeChild(card);

      const columnKey = card.getAttribute("data-card-key");
      const boardKey = column.getAttribute("data-column-key");

      this.requestDeleteCard(boardKey!, columnKey!);
    }
  }

  private cardDoubleClickEventHandler(card: HTMLInputElement) {
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
    const boardKey = clickColumn.getAttribute("data-column-key");
    
    let requestURI: string = <string>(SERVICE_URL + ADD_URI);
    const cvtURI = requestURI.replace("{boardKey}", boardKey);
    const body: any = { contents: input.value };

    fetchRequest(cvtURI, METHOD.POST, body)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        const cardId: string = data.afterCard.id;
        const cardKey: string = data.afterCard.columnKey;

        console.log(cardId, cardKey);

        cardList?.insertAdjacentHTML("afterbegin", cardTemplate(cardId, cardKey, this.inputValue));
        cardCount.innerText = cardId;

        input.value = null;
        input.focus();

        this.inputValue = "";
        clickColumn.querySelector(".add-btn").disabled = true;

        this.activity.appendActivity(data);
      });
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
