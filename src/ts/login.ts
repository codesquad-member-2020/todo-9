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
}

export default Login;
