import "../style/login.css";
import fetchRequest from "./common/fetchRequest";
import { SERVICE_URL, LOGIN_URI } from "./common/configs";
import { METHOD } from "./common/constants";
import main from "./index";

class Login {
  render() {
    return `
    <div class="container">
      <div class="top"></div>
      <div class="bottom"></div>
      <div class="center">
        <h2>Please Sign In</h2>
        <button class="login-btn">Login</button>
        <h2>&nbsp;</h2>
      </div>
    </div>  
    `;
  }

  registerEventListener() {
    document.addEventListener("click", ({ target }: Event) => {
      if ((<HTMLElement>target).className === "login-btn") this.requestLogin();
    });
  }

  requestLogin() {
    fetchRequest(SERVICE_URL + LOGIN_URI, METHOD.POST)
      .then((response) => response.json())
      .then((resData) => {
        if (!resData.result) alert(resData.message);
        return resData.result;
      })
      .then((result) => {
        if (result) main();
      });
  }
}

export default Login;
