Behaviour.specify(".ec-list__button button", "ec-list__button", 0, function(button) {
  button.onclick = function() {
    let tr = button.closest("tr");
    console.log(button)
    let container = button.closest("#ec-last-agent-container");
    let rows = tr.querySelectorAll(".ec-hidden");
    for (row of rows) {
      row.classList.toggle("jenkins-hidden");
    }
    if (button.dataset.hidden === "true") {
      button.dataset.hidden = "false";
      button.setAttribute("tooltip", container.dataset.hideText);
    } else {
      button.dataset.hidden = "true";
      button.setAttribute("tooltip", container.dataset.showText);
    }
    Behaviour.applySubtree(tr);
  }
});