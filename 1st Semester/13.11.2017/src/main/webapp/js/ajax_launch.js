$(document).ready(function(){

    var countries = $("select#countries").val();
    var select = $('#car');
    var element = document.getElementById('#countries');

    function make_ajax(){

        $.get({

            url: "/ajax_example",
            dataType: "json",
            countriesType: countries,

            success: function(data){

                select.find('option').remove();

                $.each(data.carData, function(index, value) {
                    $('option').val(value).text(value).appendTo(select);
                });

            }

        });

    }

    element.onChange = make_ajax();

});