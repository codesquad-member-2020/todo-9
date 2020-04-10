import "../style/reset.css";

import Column from "./column";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import Activity from "./activity";

import { qs$ } from "./lib/util";
import fetchRequest from "./common/fetchRequest";
import { SERVICE_URL, INIT_DATA_URI } from "./common/constants";

window.addEventListener("DOMContentLoaded", (event) => {
  const activity = new Activity();
  const editNote = new EditNote(activity, "edit-note-modal");
  const editColumn = new EditColumn(activity, "edit-column-modal");
  const column = new Column(activity, editNote, editColumn);

  /*
    1. render
    Column, EditNote, EditColumn 의 render() 함수를 호출하여
    body 에 innerHTML 로 렌더링.
  */
  document.body.innerHTML = column.render() + editNote.render() + editColumn.render();

  editNote.registerEventListener();

  /*
    2. registerEventListender

    editNote.registerEventListener();
    editcolumn.registerEventListener();
    */
  // column.registerEventListener();

  /*
    3. fetch
    BE 에 초기 데이테를 요청하여 Column 에 전달.
    */
  fetchRequest(SERVICE_URL + INIT_DATA_URI, "GET")
    .then((response) => response.json())
    .then((data) => column.receiveInitialData(data))
    .then(() => column.registerEventListener());
});
