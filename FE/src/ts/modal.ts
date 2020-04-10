import View from "./view";

interface IModal extends View {
  isVisible(): boolean;
  showModal(columnId: string, cardId: string, content: string, target: Event): void;
  hideModal(): void;
}

class Modal implements IModal {
  private visible: boolean = false;
  protected className: string;
  protected currentEvent: any = null;

  constructor(className: string) {
    this.className = className;
  }

  render(): string {
    return ``;
  }

  registerEventListener(): void {
  }

  public isVisible(): boolean {
    return this.visible;
  }

  public showModal(columnId: string, cardId: string, content: any, target: Event): void {
    const textElement = this.getTextElement();
    (<HTMLInputElement>textElement).value = content;
    this.currentEvent = target;
    this.currentEvent = (<HTMLElement>this.currentEvent.currentTarget).querySelector(".card-content");
   
    this.changeModalDisplay('block');
    this.setVisible(true);
  }

  public hideModal(): void {
    const textElement = this.getTextElement();
    (<HTMLInputElement>textElement).value = ``;
    this.currentEvent = null;

    this.changeModalDisplay('none');
    this.setVisible(false);
  }

  private setVisible(visible: boolean): void {
    this.visible = visible;
  }

  private getVisible(): boolean {
    return this.visible;
  }

  public getTextElement(): HTMLInputElement {
    const modalComponent = document.querySelector('.' + this.className);
    const textElement = modalComponent?.querySelector('.text-area');
    return <HTMLInputElement>textElement;
  }

  private changeModalDisplay(option: string) {
    const modalComponent = <HTMLElement>(document.querySelector('.' + this.className));
    modalComponent.style.display = option;
  }
}

export default Modal;