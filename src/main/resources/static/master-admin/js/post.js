$(document).ready(function () {
  $(".previewButton").on("click", function () {
    // Get the content from the input field with id 'contentData'
    var content = $("#contentData").val();

    // Set the content inside the modal body with id 'previewContainer'
    $("#previewContainer").html(content);

    // Show the modal
    var previewModal = new bootstrap.Modal(
      document.getElementById("previewModal")
    );
    previewModal.show();
  });

  //modal for add new post
  // $(".");

  //   method to update post
  $(".editBtn").on("click", function () {
    event.preventDefault();
    document.getElementById("loader").style.visibility = "visible";
    var link = $(this).attr("href");
    // Make the AJAX call to fetch the data
    $.ajax({
      url: link,
      method: "GET",
      success: function (data) {
        // Assuming 'data' is an object with the fields you need
        console.log(data);
        $("#update-postId").val(data.id);
        $("#update-fullTitle").val(data.fullTitle);
        $("#update-title").val(data.title);
        $("#update-url-slug").val(data.slug);
        $("#update-date").val(data.date);
        $("#update-contentData").val(data.content);

        // Check the categories and set the checkboxes accordingly
        data.categories.forEach(function (category) {
          $('input[name="categories"][value="' + category + '"]').prop(
            "checked",
            true
          );
        });
        document.getElementById("loader").style.visibility = "hidden";
      },
      error: function (err) {
        document.getElementById("loader").style.visibility = "hidden";
        // Handle any errors
        alert("Error fetching data: " + err.responseText);
      },
    });
    // Show the modal
    var updatePostMdl = new bootstrap.Modal(
      document.getElementById("postUpdateModal")
    );
    updatePostMdl.show();
    event.preventDefault();
  });
});
