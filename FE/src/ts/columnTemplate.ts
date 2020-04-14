const getColumnWrap = (): string => {
  return `
  <div class="header">
    <div class="header-title">TODO 서비스</div>
    <div class="menu">menu</div>
  </div>
  <div class="column-wrap">
  </div>
  `;
};

const getColumnHeader = (cardName: string, cards: Array<Object>): string => {
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
};

const getCardInput = (): string => {
  return `
  <div class="input-wrap hidden">
    <textarea id="card-input" placeholder="Enter a note"></textarea>
    <div class="btn-wrap">
      <button disabled="disabled" class="btn add-btn">Add</button>
      <button class="btn cancel-btn">Cancel</button>
    </div>
  </div>
  `;
};

const getCardContents = (id: string, cards: Array<Object>): string => {
  if (!cards) return "";
  const cardContent: string = cards
    .map((card: any): string => cardTemplate(id, card.id, card.contents))
    .join("\n");

  return cardContent;
};

const cardTemplate = (id: string, cardId: string, content: string): string => {
  return `
      <div class="card" draggable="true" data-column-id="${id}" data-card-id="${cardId}">
        <div class="card-icon">
          <span class="material-icons">description</span>
        </div>
        <span class="close card-close">&times;</span>
        <div class="content-wrap">
          <div class="card-content">${content}</div>
          <div class="card-author">Added by <span>choisohyun</span></div>
        </div>
      </div>
    `;
};

export { getColumnWrap, getColumnHeader, getCardInput, getCardContents, cardTemplate };
