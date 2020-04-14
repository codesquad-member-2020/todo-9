class TodoDataModel {
  private static instance: TodoDataModel;
  private todoList: any

  constructor() {
    this.todoList = [];
  }

  public static getInstance(): TodoDataModel {
    if (!TodoDataModel.instance) {
      TodoDataModel.instance = new TodoDataModel();
    }

    return TodoDataModel.instance;
  }

  setInitialData(data: any) {
    data.columns.forEach((column: any) => {
      this.todoList[column.id] = {name: column.name, boardKey: column.boardKey, createdUserId: column.createdUserId, cards: []};

      column.cards.forEach((card: any) => {
        this.todoList[column.id][card.id] =
        {
          columnKey: card.columnKey,
          column: card.column,
          updateUserId: card.updatedUserId,
          contents: card.contents
        };
      });
    });

    console.log(this.todoList);
  }

  getCardColumnKey(boardId: number, cardId: number) {
    return this.todoList[boardId][cardId].columnKey;
  }

  getColumnBoardKey(boardId: number) {
    return this.todoList[boardId].boardKey;
  }

  appendCard(columnId: number, cardId: number, columnKey: number, column: number, updateUserId: string, contents: string) {
    this.todoList[columnId][cardId] =
    {
      columnKey: columnKey,
      column: column,
      updateUserId: updateUserId,
      contents: contents
    };
  }

  deleteCard(boardId: number, cardId: number) {
    this.todoList[boardId][cardId] = undefined;
  }
}

export default TodoDataModel;