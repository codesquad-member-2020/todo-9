import '../style/edit-column.css';
import Activity from './activity'
import Modal from './modal';

class EditColumn extends Modal {
  private activity: Activity;

  constructor(activity: Activity, className: string) {
    super(className);
    this.activity = activity;
  }

  render(): string {
    return `
    <div class="edit-column-modal">
      <div class="modal-container">
        <div class="modal-header">
          <div class="title-area">
            <h1>Edit column name</h1>
          </div>
          <div class="button-area">
            <button class="close-button">×</button>  
          </div>
        </div>
        <div class="modal-body">
          <h1>Column name</h1>
          <input class="text-area" type="text" placeholder="Enter a column name(To Do, In Progress, Done)" autofocus></input>
          <button class="save-button">Save note</button>
        </div>
      </div>
    </div>
    `;
  }

  registerEventListener(): void {
  }
}

export default EditColumn;