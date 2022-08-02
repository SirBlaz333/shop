$('#localization').on('change', function() {
    let formData = $('#localization').serializeArray();
    var url = new URL(window.location.href);
    url.searchParams.set(formData[0].name, formData[0].value);
    $.ajax({
        url: url,
        type: 'GET',
        data: formData,
        cache: false,
        success: function (data) {
            window.history.pushState("Hallo", "Title", url);
            location.reload();
        }
    });
});