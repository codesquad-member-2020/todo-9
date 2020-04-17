import "../style/edit-note.css";
import Activity from "./activity";
import Modal from "./modal";
import fetchRequest from "./common/fetchRequest";
import updateDataset from "./updateDataset";
import { SERVICE_URL, INIT_DATA_URI, EDIT_DATA_URI } from "./common/configs";
import { METHOD } from "./common/constants";

class EditNote extends Modal {
  private activity: Activity;

  constructor(activity: Activity, className: string) {
    super(className);
    this.activity = activity;
  }

  render(): string {
    return `
    <div class="edit-note-modal">
      <div class="modal-container">
        <div class="modal-header">
          <div class="title-area">
            <h1>Edit note</h1>  
          </div>
          <div class="button-area">
            <button class="close-button">×</button>  
          </div>
        </div>
        <div class="modal-body">
          <h1>Note</h1>
          <textarea class="text-area" autofocus></textarea>
          <button class="save-button">Save note</button>
        </div>
      </div>
    </div>
    `;
  }

  registerEventListener(): void {
    const modal = document.querySelector("." + this.className);

    modal?.addEventListener("click", (evt) => {
      const className = (<HTMLElement>evt.target).className;

      switch (className) {
        case "save-button": {
          this.saveButtonHandler(evt);
          break;
        }
        case "close-button": {
          this.cancelButtonHandler(evt);
          break;
        }
      }
    });

    modal?.addEventListener("input", (evt) => {
      const className = (<HTMLElement>evt.target).className;

      switch (className) {
        case "text-area": {
          this.inputHandler(evt);
          break;
        }
      }
    });
  }

  requestEditCard(boardKey: string, columnKey: string, body: any) {
    let requestURI: string = <string>(SERVICE_URL + EDIT_DATA_URI);
    const cvtURI = requestURI.replace("{boardKey}", boardKey).replace("{columnKey}", columnKey);

    fetchRequest(cvtURI, METHOD.PUT, body)
      .then((response) => response.json())
      .then((data) => {
        this.activity.appendActivity(data);
      });
  }

  saveButtonHandler(evt: Event): void {
    const textElement = this.getTextElement();
    this.currentTargetElement.innerHTML = textElement.value;

    const card: any = (<HTMLElement>this.currentTargetElement).closest(".card");
    const column: any = (<HTMLElement>this.currentTargetElement).closest(".column");
    const body = { contents: textElement.value };

    const columnKey = card.getAttribute("data-card-key");
    const boardKey = column.getAttribute("data-column-key");

    this.requestEditCard(boardKey, columnKey, body);
    this.hideModal();
  }

  cancelButtonHandler(evt: Event): void {
    this.hideModal();
  }

  inputHandler(evt: Event): void {
    //Todo: 객체 탐색하는 코드 없애보기. (렌더링 후 캐싱 등을 통해서)
    const modal = <Element>document.querySelector("." + this.className);
    const saveButton: any = <HTMLElement>modal?.querySelector(".save-button");
    const length = (<HTMLInputElement>evt.target).value.length;

    if (!length) {
      saveButton.disabled = true;
    } else {
      saveButton.disabled = false;
    }
  }
}

export default EditNote;
