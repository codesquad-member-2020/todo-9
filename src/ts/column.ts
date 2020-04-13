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
import { qs$, addClass, removeClass } from "./lib/util";
import { DELETE_MESSAGE } from './common/confirmMessage';

class Column implements IView {
  private activity: Activity;
  private editNote: EditNote;
  private editColumn: EditColumn;
  private placeHolderVisible: boolean;
  private inputValue: string;

  constructor(activity: Activity, editNote: EditNote, editColumn: EditColumn) {
    this.activity = activity;
    this.editNote = editNote;
    this.editColumn = editColumn;
    this.placeHolderVisible = false;
    this.inputValue = "";
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

    document.body.addEventListener("click", (evt : Event) => {
      const className = (<HTMLInputElement>evt.target).className;

      if (className === 'close card-close') {
        this.cardDeleteClickEventHandler(evt);
      }
    });

    document.body.addEventListener("dblclick", (evt : Event) => {
      const className = (<HTMLInputElement>evt.target).className;
      
      if (className === "content-wrap") {
        const contentWrap = (<HTMLInputElement>evt.target);
        const cardElement: any = contentWrap.closest(".card");
        this.cardDoubleClickEventHandler(cardElement);
      }
    });

    qs$(".column-wrap").addEventListener("input", (evt: Event) => {
      this.inputEventHandler(evt);
    });
  }

  showConfirm(message: string):boolean {
    const result = confirm(message);
    return result;
  }

  cardDeleteClickEventHandler(evt: Event) {
    const result = this.showConfirm(DELETE_MESSAGE);

    if (result) {
      const target = <HTMLInputElement>evt.target
      const test: any = target.closest(".card-content-wrap");
      const column: any = target.closest(".card-wrap");
      column.removeChild(test);
    }
  }

  cardDoubleClickEventHandler(card:HTMLInputElement) {
    const contentElement: any = card.querySelector('.card-content');
    const content = contentElement.innerHTML;
    const cardId = card.id;
    const columnId: any = card.closest('.column')?.id;

    this.editNote.showModal(columnId, cardId, content, contentElement);
  }

  cardAddBtnClickEventHandler(clickColumn: any) {
    clickColumn
      .querySelector(".card-list-wrap")
      .insertAdjacentHTML("afterbegin", cardTemplate("0", "0", this.inputValue));

    const input: any = clickColumn.querySelector("#card-input");
    input.value = null;
    input.focus;

    this.inputValue = "";
    clickColumn.querySelector(".add-btn").disabled = true;
  }

  inputEventHandler({ target }: Event) {
    const addBtn: any = (<HTMLInputElement>target).closest(".column")?.querySelector(".add-btn");
    const DELAY_TIME: number = 300;
    this.inputValue = (<HTMLInputElement>target).value;

    let timer;
    clearTimeout(timer);
    timer = setTimeout(() => {
      if (this.inputValue === "") addBtn.disabled = true;
      addBtn.disabled = false;
    }, DELAY_TIME);
  }

  plusBtnClickEventHandler(column: HTMLInputElement | null): void {
    const cardInput = column?.querySelector(".input-wrap");

    if (!this.getPlaceHolderVisible(cardInput)) {
      addClass(cardInput, "hidden");
      this.setPlaceholderVisible(true);
      const input: any = column?.querySelector("#card-input");
      input.value = null;
      return;
    }
    removeClass(cardInput, "hidden");
    this.setPlaceholderVisible(false);
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
      const { id, name, cards }: any = element;
      columnTemplate += `
      <div class="project-columns">
        <div class="column" id="c${id}">
          ${getColumnHeader(name, cards)}
          <div class="card-wrap">
            ${getCardInput()}
            <div class="card-list-wrap">
              ${getCardContents(id, cards)}
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
