Behaviour.specify(".ec-toggle-project", "extra-column-toggle-project", 0, function (e) {
  e.addEventListener("click", function () {
    const request = e.dataset.request;
    fetch(request, {
      method: "post",
      headers: crumb.wrap({
        "Content-Type": "application/x-www-form-urlencoded",
      }),
    });
    setTimeout("window.location.reload()", 1000);
  });
});
