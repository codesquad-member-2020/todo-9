import "../style/login.css";
import fetchRequest from "./common/fetchRequest";
import { qs$ } from "./lib/util";
import { SERVICE_URL, LOGIN_URI } from "./common/configs";
import { METHOD } from "./common/constants";

class Login {
  private isSuccess: boolean;

  constructor() {
    this.isSuccess = false;
  }

  render(): string {
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

  async registerEventListener() {
    await qs$(".login-btn").addEventListener("click", this.requestLogin);
  }

  requestLogin = () => {
    fetchRequest(SERVICE_URL + LOGIN_URI, METHOD.POST)
      .then((response) => response.json())
      .then((resData) => this.handleResponse(resData))

      .catch((error) => console.error(error));
  };

  handleResponse(resData: any) {
    if (!resData.result) alert(resData.message);
    this.isSuccess = resData.result;
    return this.isSuccess;
  }
}

export default Login;
