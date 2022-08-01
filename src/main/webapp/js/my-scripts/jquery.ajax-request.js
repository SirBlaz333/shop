function doAsyncRequest(e){
    let form = e.form;
    let formData = $(form).serializeArray();
    $.ajax({
            url: 'controller',
            type: 'POST',
            data: formData,
            cache: false,
            success: function (data) {
                $("#cart-async").load(location.href + " #cart-async");
                $("#async").load(location.href + " #async");
            }
        });
}