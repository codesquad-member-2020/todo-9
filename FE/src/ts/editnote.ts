import '../style/edit-note.css';
import Activity from './activity'
import Modal from './modal';

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
    const modal = document.querySelector('.' + this.className);

    modal?.addEventListener('click', evt => {
      const className = (<HTMLElement>evt.target).className;

      switch (className) {
        case 'save-button': {
          this.saveButtonHandler(evt)
          break;
        }
        case 'close-button': {
          this.cancelButtonHandler(evt)
          break;
        }
      }
    });

    modal?.addEventListener('input', evt => {
      const className = (<HTMLElement>evt.target).className;

      switch (className) {
        case 'text-area': {
          this.inputHandler(evt)
          break;
        }
      }
    });
  }

  saveButtonHandler(evt: Event): void {
    const textElement = this.getTextElement();
    this.currentTargetElement.innerHTML = textElement.value;

    this.hideModal();
  }

  cancelButtonHandler(evt: Event): void {
    this.hideModal();
  }

  inputHandler(evt: Event): void {
    //Todo: 객체 탐색하는 코드 없애보기. (렌더링 후 캐싱 등을 통해서)
    const modal = <Element>document.querySelector('.' + this.className);
    const saveButton: any = <HTMLElement>modal?.querySelector('.save-button');
    const length = (<HTMLInputElement>evt.target).value.length;

    if (!length) {
      saveButton.disabled = true;
    }
    else {
      saveButton.disabled = false;
    }
  }
}

export default EditNote;