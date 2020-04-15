import "../style/reset.css";

import Column from "./column";
import EditNote from "./editnote";
import EditColumn from "./editcolumn";
import Activity from "./activity";
import Login from "./login";

import fetchRequest from "./common/fetchRequest";
import { SERVICE_URL, INIT_DATA_URI } from "./common/configs";
import { METHOD } from "./common/constants";

(async () => {
  const login = new Login();
  document.body.innerHTML = login.render();

  await login.registerEventListener();
  // login.getIsSuccess()
})();

const main = () => {
  const activity = new Activity();
  const editNote = new EditNote(activity, "edit-note-modal");
  const editColumn = new EditColumn(activity, "edit-column-modal");
  const column = new Column(activity, editNote, editColumn);

  document.body.innerHTML = column.render() + editNote.render() + editColumn.render();

  editNote.registerEventListener();

  fetchRequest(SERVICE_URL + INIT_DATA_URI, METHOD.GET)
    .then((response) => response.json())
    .then((data) => {
      column.receiveInitialData(data);
    })
    .then(() => column.registerEventListener());
};
