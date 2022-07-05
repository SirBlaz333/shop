$("#uploadAvatar").submit(function(e) {

    e.preventDefault();

    var form = $(this);
    var actionUrl = form.attr('action');
    var data = new FormData(document.getElementById("uploadAvatar"));
    data.append("command", "uploadImage");

    $.ajax({
        type: "POST",
        url: actionUrl,
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        success: function(data)
        {
            $("#result").text(data);
            document.getElementById('uploadForm').style.display = 'none';
        }
    });

});