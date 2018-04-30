function addNewRow(button, name) {
    var parent = button.parentNode;
    var element = document.createElement("input");
    element.setAttribute("name", name);
    parent.insertBefore(document.createElement("br"), button);
    parent.insertBefore(element, button);
}
