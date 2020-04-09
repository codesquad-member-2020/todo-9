import '../style/style.css';

import View from "./view"
import Activity from './activity'
import EditNote from './editnote'
import EditColumn from './editcolumn'
import { qs$ } from './lib/util';

class Column implements View {
  private activity: Activity;
  private editNote : EditNote;
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
    `;
  }

  registerEventListener(): void {
    qs$(".column-wrap").addEventListener('click', ({target}:Event) => {
      console.log((<HTMLInputElement>target).className);

      //만약 Column 영역의 '+' 버튼이 눌리면
      //plusBtnClickEventHandler(<HTMLInputElement>target);
    });

    qs$(".column-wrap").addEventListener('input', ({target}:Event) => {
      console.log((<HTMLInputElement>target).value);
    });
  }

  receiveInitialData(initialData: any): void {
    //insertAdjacentHTML 사용
  }

  //Column 영역의 '+' 버튼을 클릭 했을 때 호출되는 핸들러
  plusBtnClickEventHandler(target: HTMLInputElement): void {
    //Placeholder 가 닫혀있다면
      //Placeholder 를 open
    //Placeholder 가 열려잇다면
      //Placeholder 를 close

    // if (this.getPlaceHolderVisible() === true) {
    //   //DOM 조작을 통한 classList remove
    //   this.setPlaceholderVisible(false);
    // }
    // else {
    //   //DOM 조작을 통한 classList add
    //   this.setPlaceholderVisible(true);
    // }
  }

  setPlaceholderVisible(visible: boolean): void {
    this.placeHolderVisible = visible;
  }

  getPlaceHolderVisible(): boolean {
    return false;
  }
}

export default Column;