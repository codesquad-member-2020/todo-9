import "../style/reset.css";

import Column from "./column";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import Activity from "./activity";

import fetchRequest from "./common/fetchRequest";
import { configs } from "./common/configs";
import { METHOD } from "./common/constants";

window.addEventListener("DOMContentLoaded", (event) => {
  const activity = new Activity();
  const editNote = new EditNote(activity, "edit-note-modal");
  const editColumn = new EditColumn(activity, "edit-column-modal");
  const column = new Column(activity, editNote, editColumn);

  document.body.innerHTML = column.render() + editNote.render() + editColumn.render();

  editNote.registerEventListener();

  fetchRequest(configs.apiUrl, METHOD.GET)
    .then((response) => response.json())
    .then((data) => column.receiveInitialData(data))
    .then(() => column.registerEventListener());
});
