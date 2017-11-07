function add_div(){
    var div = document.createElement('div');
    div.className = "show-div";
    div.id = "show-div";

    document.getElementById('delete_button').removeAttribute('disabled');

    document.body.appendChild(div);
}

function remove_div(){
	var element = document.getElementById('show-div');
	element.parentNode.removeChild(element);
}