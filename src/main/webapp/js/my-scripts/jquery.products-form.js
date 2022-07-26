var filterData = $("#filtering").serialize();
var sortingData = $("#sorting").serialize();

$("#filtering").submit(function(e){
    e.preventDefault();
    filterData = $(this).serializeArray();
    submitForms();
});

$('select').on('change', function() {
    sortingData = $('#sorting').serializeArray();
    submitForms();
});

function submitForms(){
    let url = "controller?command=products";
    url = appendParamToUrl(url, filterData);
    url = appendParamToUrl(url, sortingData);
    $.ajax({
            url: url,
            type: 'GET',
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                window.history.pushState("Hallo", "Title", url);
                $("#products").load(location.href + " #products");
            }
        });
}

function appendParamToUrl(url, array){
    for (var i=0; i<array.length; i++){
        if(array[i].name !== undefined && array[i].value !== ""){
            url += "&" + array[i].name + "=" + array[i].value;
        }
    }
    return url;
}