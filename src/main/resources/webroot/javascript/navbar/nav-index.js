window.onload = function () {
    let navbar = document.getElementById("navbar");
    if ((window).scrollY > 100) {
        navbar.classList.add("navbar-dark");
    } else {
        navbar.classList.remove("navbar-dark");
    }
    if ((window).scrollY > 2000) {
        navbar.style.display = 'none';
    } else {
        navbar.style.display = 'block';
    }
}