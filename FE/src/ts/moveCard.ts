class MoveCard {
  private draggingCard: HTMLElement | null;
  private destCard: HTMLElement | null;
  private startColumn: HTMLElement | null;
  private destColumn: HTMLElement | null;

  constructor() {
    this.draggingCard = null;
    this.destCard = null;
    this.startColumn = null;
    this.destColumn = null;
  }

  isAbove(nodeA: HTMLElement, nodeB: HTMLElement) {
    const rectA = nodeA.getBoundingClientRect();
    const rectB = nodeB.getBoundingClientRect();

    return rectA.top + rectA.height / 2 < rectB.top + rectB.height / 2;
  }

  dragStartEventHandler({ target }: Event) {
    this.draggingCard = <HTMLElement>target;
    this.draggingCard.style.opacity = "0.5";
    this.startColumn = this.draggingCard.closest(".project-columns");
  }

  dragOverEventHandler(evt: Event) {
    evt.preventDefault();
  }

  dragEnterEventHandler({ toElement }: Event) {
    this.destCard = toElement.closest(".card");
    this.destColumn = toElement.closest(".project-columns");
    this.draggingCard!.classList.add("placeholder");

    if (this.destCard && this.isAbove(this.draggingCard!, this.destCard)) {
      this.destCard.before(this.draggingCard!);
    } else if (this.destCard && this.isAbove(this.destCard, this.draggingCard!)) {
      this.destCard.after(this.draggingCard!);
    } else if (this.destColumn) {
      this.destColumn!.querySelector(".card-list-wrap")?.appendChild(this.draggingCard!);
    }
  }

  dragEndEventHandler({ target }: Event) {}
}

export { MoveCard };
