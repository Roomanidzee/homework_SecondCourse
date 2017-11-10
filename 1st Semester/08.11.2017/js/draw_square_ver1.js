$(document).ready(function(){

    var c = document.getElementById("my-canvas");
    var ctx = c.getContext("2d");

    function move(x, y){

       ctx.beginPath();
       ctx.lineWidth = "2";
       ctx.strokeStyle = "green";
       ctx.rect(x, y, 50, 50);
       ctx.stroke();

       x = x + 10;
       y = y + 10;

    }

    ctx.beginPath();
    ctx.lineWidth = "4";
    ctx.strokeStyle = "red";
    ctx.rect(5, 5, 100, 100);
    ctx.stroke();

    var x = 0;
    var y = 0;

    $('my-canvas').animate(
    	{height: "show",
    	step: move(x, y)},
    	3000
    );    


});