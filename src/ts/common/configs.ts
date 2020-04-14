export const SERVICE_URL = "http://15.165.21.99/";
export const INIT_DATA_URI = "api/board";
export const EDIT_DATA_URI = "api/column/{boardKey}/card/{columnKey}"
export const DELETE_DATA_URI = "api/column/{boardKey}/card/{columnKey}"
export const CHANGE_DATA_URI = "";

export const configs = {
  apiUrl: process.env.SERVICE_API || "localhost",
  username: process.env.USERNAME || "root",
  password: process.env.PASSWORD || "admin",
};
