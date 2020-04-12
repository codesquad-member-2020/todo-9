import "../style/style.css";

import View from "./view";
import Activity from "./activity";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import { qs$, addClass, removeClass } from "./lib/util";
import { DELETE_MESSAGE } from './common/confirmMessage';

class Column implements View {
  private activity: Activity;
  private editNote: EditNote;
  private editColumn: EditColumn;
  private placeHolderVisible: boolean;

  constructor(activity: Activity, editNote: EditNote, editColumn: EditColumn) {
    this.activity = activity;
    this.editNote = editNote;
    this.editColumn = editColumn;
    this.placeHolderVisible = false;
  }

  render(): string {
    return `
    <div class="header">
      <div class="header-title">TODO 서비스</div>
      <div class="menu">menu</div>
    </div>
    <div class="column-wrap">
    </div>
    `;
  }

  registerEventListener(): void {
    qs$(".column-wrap").addEventListener("click", ({ target }: Event) => {
      console.log((<HTMLInputElement>target).className);

      if ((<HTMLInputElement>target).className === "add") {
        this.plusBtnClickEventHandler();
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

    // qs$(".content-wrap").addEventListener("dblclick", ( evt : Event) => {
    //   const className = (<HTMLInputElement>evt.target).className;
    //   if (className !== "close")
    //     this.cardDoubleClickEventHandler(evt)
    // });

    qs$(".column-wrap").addEventListener("input", ({ target }: Event) => {
      console.log((<HTMLInputElement>target).value);
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

  receiveInitialData({ columns }: any): void {
    let columnTemplate: string = "";
    columns.forEach((element: Element) => {
      const { id, name, createdAt, updatedAt, archivedAt, order, cards, archived }: any = element;
      columnTemplate += `
      <div class="project-columns">
        <div class="column" id="c${id}">
          ${this.getColumnHeader(name, cards)}
          <div class="card-wrap">
          ${this.getCardInput()}
          ${this.getCardContents(id, cards)}
          </div>
        </div>
      </div>
      `;
    });
    qs$(".column-wrap").innerHTML = columnTemplate;
  }

  getColumnHeader(cardName: string, cards: Array<Object>): string {
    const cardCount: number = cards ? cards.length : 0;
    return `
    <div class="column-header">
      <div class="column-title-wrap">
        <span class="card-count">${cardCount}</span>
        <span class="column-title">${cardName}</span>
      </div>
      <div class="column-icon-wrap">
        <span class="add">&plus;</span>
        <span class="close">&times;</span>
      </div>
    </div>
    `;
  }

  getCardInput(): string {
    return `
    <div class="input-wrap hidden">
      <textarea id="card-input" placeholder="Enter a note"></textarea>
      <div class="btn-wrap">
        <button disabled="disabled" class="btn add-btn">Add</button>
        <button class="btn cancel-btn">Cancel</button>
      </div>
    </div>
    `;
  }

  getCardContents(id: string, cards: Array<Object>): string {
    if (!cards) return "";

    let cardTemplate: string = "";

    cards.forEach((element: any) => {
      cardTemplate += `
      <div class="card-content-wrap">
        <div class="card" id="c${id}-${element.id}">
          <div class="card-icon">
            <span class="material-icons">description</span>
          </div>
          <span class="close card-close">&times;</span>
          <div class="content-wrap">
            <div class="card-content">${element.contents}</div>
            <div class="card-author">Added by <span>choisohyun</span></div>
          </div>
        </div>
      </div>
      `;
    });

    return cardTemplate;
  }

  //Column 영역의 '+' 버튼을 클릭 했을 때 호출되는 핸들러
  plusBtnClickEventHandler(): void {
    //Placeholder 가 닫혀있다면
    //Placeholder 를 open
    //Placeholder 가 열려잇다면
    //Placeholder 를 close
    if (this.getPlaceHolderVisible()) {
      //DOM 조작을 통한 classList remove
      this.setPlaceholderVisible(false);
    } else {
      //DOM 조작을 통한 classList add
      this.setPlaceholderVisible(true);
    }
  }

  setPlaceholderVisible(visible: boolean): void {
    this.placeHolderVisible = visible;
  }

  getPlaceHolderVisible(): boolean {
    return false;
  }
}

export default Column;
