import '../style/reset.css';

import Column from './column'
import EditNote from './editnote'
import EditColumn from './editcolumn'
import Activity from './activity'

import {qs$} from './lib/util'
import fetchRequest from './common/fetchRequest'

window.addEventListener('DOMContentLoaded', (event) => {
  const activity = new Activity();
  const editNote = new EditNote(activity);
  const editColumn = new EditColumn(activity);
  const column = new Column(activity, editNote, editColumn);

  /*
    1. render
    Column, EditNote, EditColumn 의 render() 함수를 호출하여
    body 에 innerHTML 로 렌더링.

    ex)
    document.body.innerHTML = column.render() + editNote.render() + editColumn.render()
  */

  /*
    2. registerEventListender

    column.registerEventListener();
    editNote.registerEventListener();
    editcolumn.registerEventListener();
  */
  
   /*
    3. fetch
    BE 에 초기 데이테를 요청하여 Column 에 전달.

    ex)
    fetchRequest(SERVICE_URL.INFORMATION, "GET")
    .then(response => response.json())
    .then(data => {
      column.receiveInitialData(data);
    });
  */
});