interface IView {
  render(): string;
  registerEventListener(): void;
}

export default IView;