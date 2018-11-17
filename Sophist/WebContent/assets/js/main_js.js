function registerFormCheck() {
    var length = document.forms[0].length - 1; // submit을 제외한 모든 input태그의 갯수를 가져옴

    for (var i = 0; i < length; i++) {

        if (document.forms[0][i].value == null || document.forms[0][i].value == "") {

            alert(document.forms[0][i].name + "을/를 입력하세요."); // 경고창을 띄우고

            document.forms[0][i].focus();                       // null인 태그에 포커스를 줌

            return false;
        }
    }
}

function dropDown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {

        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}