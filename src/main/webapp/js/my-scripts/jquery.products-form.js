var filterData = $("#filtering").serialize();
var sortingData = $("#sorting").serialize();
var paginationData = $("#pagination").serialize();

$(document).ready(function(){
    document.getElementById("pageCount").value = "1";
});

$("#filtering").submit(function(e){
    e.preventDefault();
    filterData = $(this).serializeArray();
    submitForms();
});

$("#pagination").submit(function(e){
    e.preventDefault();
    paginationData = $(this).serializeArray();
    submitForms();
});

$('#sortingCriteria').on('change', function() {
    sortingData = $('#sorting').serializeArray();
    submitForms();
});

$('#sortingOrder').on('change', function() {
    sortingData = $('#sorting').serializeArray();
    submitForms();
});

function submitForms(){
    createURL();
    $.ajax({
            url: url,
            type: 'GET',
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                window.history.pushState("Hallo", "Title", url);
                $("#products").load(location.href + " #products");
                $("#pageInfo").load(location.href + " #pageInfo");
            }
        });
}

function createURL(){
    url = "controller?command=products";
    url = appendParamToUrl(url, filterData);
    url = appendParamToUrl(url, sortingData);
    url = appendParamToUrl(url, paginationData);
}

function appendParamToUrl(url, array){
    for (var i=0; i<array.length; i++){
        if(array[i].name !== undefined && array[i].value !== ""){
            url += "&" + array[i].name + "=" + array[i].value;
        }
    }
    return url;
}

$('#nextPage').on('click', function(e){
    e.preventDefault();
    let pageCount = parseInt(document.getElementById("pageCount").value) + 1;
    let maxPages = document.getElementById("maxPages").value;
    if(pageCount <= maxPages){
        document.getElementById("pageCount").value = "" + pageCount;
        paginationData = $("#pagination").serializeArray();
        submitForms();
    }
});

$('#previousPage').on('click', function(e){
    e.preventDefault();
    let pageCount = parseInt(document.getElementById("pageCount").value) - 1;
    if(pageCount > 0){
        document.getElementById("pageCount").value = "" + pageCount;
        paginationData = $("#pagination").serializeArray();
        submitForms();
    }
});