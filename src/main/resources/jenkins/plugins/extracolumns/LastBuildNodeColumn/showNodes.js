Behaviour.specify(".ec-list__button button", "ec-list__button", 0, function(button) {
  button.onclick = function() {
    let tr = button.closest("tr");
    let table = button.closest("table");
    let rows = tr.querySelectorAll(".ec-hidden");
    for (row of rows) {
      row.classList.toggle("jenkins-hidden");
    }
    if (button.dataset.hidden === "true") {
      button.dataset.hidden = "false";
      button.setAttribute("tooltip", table.dataset.hideText);
    } else {
      button.dataset.hidden = "true";
      button.setAttribute("tooltip", table.dataset.showText);
    }
    Behaviour.applySubtree(tr);
  }
});