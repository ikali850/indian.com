$(document).ready(function () {
  var lastScrollTop = 0;
  var header = $("#header");

  $(window).scroll(function () {
    var currentScroll = $(this).scrollTop();

    if (currentScroll > lastScrollTop) {
      // Scrolling down
      header.removeClass("d-block").addClass("d-none");
    } else {
      // Scrolling up
      header.removeClass("d-none").addClass("d-block");
    }

    lastScrollTop = currentScroll;
  });
});
// function to auto scroll news
$(document).ready(function () {
  $("#news-list").easyTicker({
    direction: "up",
    easing: "swing",
    speed: "slow",
    interval: 1000,
    height: "500px",
    visible: 0,
    mousePause: true,
    controls: {
      up: "#up",
      down: "#down",
      toggle: "#toggle",
      playText: "",
      stopText: "",
    },
    callbacks: {
      before: false,
      after: false,
    },
  });
});
