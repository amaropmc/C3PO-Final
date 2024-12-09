import { element } from "/js/views/components/commons/element.js";

export function div(classNames = []) {
  const div = element("div", classNames);

  return div;
}
