import "../style/style.css";

import View from "./view";
import Activity from "./activity";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import { qs$, addClass, removeClass } from "./lib/util";

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

    qs$(".card").addEventListener("dblclick", (evt: Event) =>
      this.cardDoubleClickEventHandler(evt)
    );

    qs$(".column-wrap").addEventListener("input", ({ target }: Event) => {
      console.log((<HTMLInputElement>target).value);
    });
  }

  cardDoubleClickEventHandler() {}

  receiveInitialData({ columns }: any): void {}

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
