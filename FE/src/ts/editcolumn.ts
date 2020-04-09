import '../style/edit-column.css';
import Activity from './activity'
import View from "./view"

class EditColumn implements View{
  private activity: Activity;

  constructor(activity: Activity) {
    this.activity = activity;
  }

  render(): string {
    return ``;
  }

  registerEventListener(): void {
    
  }

  showModal(columnId: string, cardId: string, content: string, target: HTMLInputElement): void {
    
  }
}

export default EditColumn;