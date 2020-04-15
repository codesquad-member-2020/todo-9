import "../style/activity.css";

import IView from "./view"
import { qs$ } from "./lib/util";
import UserCardAction from "./common/usercardaction";

class Activity implements IView{
  constructor() {
  }

  public render(): string {
    return `
    <div class="activity">
      <div class="title-area">
        <div class="icon">
          <i class="fas fa-bars"> Menu</i>
        </div>
        <button>×</button>
      </div>
      <div class="activities">
      </div>        
    </div>
    `;
  }

  public registerEventListener(): void {
    const closeButton: any = qs$(".activity .title-area button");
    
    closeButton.addEventListener('click', (evt: Event) => {
      this.hideActivity();
    })
  }

  public receiveInitialData({ logs }: any) {
    logs.forEach((log: any) => {
      this.appendActivity(log);
    });
  }

  public showActivity(): void {
    const activityElement: HTMLElement = qs$(".activity");
    activityElement.classList.add('show');
  }

  public hideActivity(): void {
    const activityElement: HTMLElement = qs$(".activity");
    activityElement.classList.remove('show');
  }

  public appendActivity(log: any): void {
    const activityWrap: any = qs$(".activities");
    const contents = this.makeCardTemplate(log);
    activityWrap?.insertAdjacentHTML("afterbegin", contents);
  }

  private getGeneralActivityTemplate():string {
    return `
    <div class="contents">
      <div class="icon">
        <img src={image}/>
      </div>
      <div class="detail">
        <p class="description">{description}</p>
        <p class="time">{time}</p>
      </div>
    </div>
    `
  }

  private makeCardTemplate(log: any): string {
    const generalTemplate = this.getGeneralActivityTemplate();
    let description: string = '';
    const imageSrc: string = log.user.profileImageUrl;
    const time: string = log.afterCard.updatedAt;

    switch (log.action) {
      case UserCardAction.CREATE: {
        description = this.makeCreateTemplate(log);
        break;
      }
      case UserCardAction.EDIT: {
        description = this.makeEditTemplate(log);
        break;
      }
      case UserCardAction.REMOVE: {
        description = this.makeDeleteTemplate(log);
        break;
      }
      case UserCardAction.MOVE: {
        description = this.makeMoveTemplate(log);
        break;
      }
      default: {
        throw Error("Not defined action.");
      }
    }

    return generalTemplate
            .replace("{image}", imageSrc)
            .replace("{description}", description)
            .replace("{time}", time);
  }

  private makeCreateTemplate(log: any): string {
    const description = this.getDescriptionTemplate(UserCardAction.CREATE)
                                .replace("{userName}", log.user.username)
                                .replace("{contents}", log.afterCard.contents)
                                .replace("{columnName}", log.toColumn.name);
                            
    return description;
  }

  private makeDeleteTemplate(log: any): string {
    const description = this.getDescriptionTemplate(UserCardAction.REMOVE)
                                .replace("{userName}", log.user.username)
                                .replace("{contents}", log.afterCard.contents);

    return description;
  }

  private makeEditTemplate(log: any): string {
    const description = this.getDescriptionTemplate(UserCardAction.EDIT)
                                .replace("{userName}", log.user.username)
                                .replace("{afterContents}", log.afterCardContents)
                                .replace("{beforeContents}", log.beforeCardContents);

    return description;
  }

  private makeMoveTemplate(log: any): string {
    const description = this.getDescriptionTemplate(UserCardAction.MOVE)
                                .replace("{userName}", log.user.username)
                                .replace("{contents}", log.afterCard.contents)
                                .replace("{beforeColumnName}", log.fromColumn.name)
                                .replace("{afterColumnName}", log.toColumn.name);
                            
    return description;
  }

  private makePastTimeTemplate(log: any): string {
    return ``;
  }

  private getDescriptionTemplate(action: UserCardAction): string {
    return templateMap[action];
  }
}

const templateMap = {
  "create": `<strong>@{userName}</strong> added <strong>{contents}</strong> to <b>{columnName}</b>`,
  "delete": `<strong>@{userName}</strong> removed <strong>{contents}</strong>`,
  "edit": `<strong>@{userName}</strong> updated <strong>{beforeContents}</strong> to <strong>{afterContents}</strong>`,
  "move": `<strong>@{userName}</strong> moved <strong>{contents}</strong> from <b>{beforeColumnName}</b> to <b>{afterColumnName}</b>`
}

export default Activity;