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

  isAbove(nodeA: HTMLElement, nodeB: HTMLElement) {}

  dragStartEventHandler({ target }: Event) {}

  dragOverEventHandler(evt: Event) {}

  dragEnterEventHandler({ toElement }: Event) {}

  dragEndEventHandler({ target }: Event) {}
}

export { MoveCard };
