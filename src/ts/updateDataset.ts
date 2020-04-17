import { qsAll$ } from "./lib/util";

const updateDataset = (data: { fromColumn: any; toColumn: any }) => {
  const { fromColumn, toColumn } = data;
  const beforeColumn = qsAll$(".column")[fromColumn.boardKey];
  const afterColumn = qsAll$(".column")[toColumn.boardKey];
  const beforCards = beforeColumn.querySelectorAll(".card");
  const afterCards = afterColumn.querySelectorAll(".card");

  change(fromColumn, beforCards);
  change(toColumn, afterCards);
};

const change = (column, cards) => {
  const reverseCards = column.cards.reverse();

  cards.forEach((card: { dataset: { cardKey: any } }, index: any) => {
    card.dataset.cardKey = reverseCards[index].columnKey;
  });
};

export default updateDataset;
