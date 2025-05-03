'use strict';

let sign_up = document.getElementById("link_signUp");
let modal_sign_up = document.getElementById("link_modal_signUp");
let link_download = document.getElementById("link_download");
let button_download = document.getElementById("button_download");
let close_signUp = document.getElementById("close_signUp");
let close_download = document.getElementById("close_download");
let close_signIn = document.getElementById("close_signIn");
let link_signIn = document.getElementById("link_signIn");
let link_pwReset = document.getElementById("link_pwReset");
let close_pwReset = document.getElementById("close_pwReset");
let modal_pay = document.getElementById("pay_modal");


const closeAll = function() {
    let pwReset = document.getElementById("pwReset_modal");
    if(pwReset != null) { pwReset.style.display = "none"; }

    let sign_in = document.getElementById("signIn_modal");
    if(sign_in != null) {sign_in.style.display = "none";}

    let sign_up = document.getElementById("signUp_modal");
    if(sign_up != null) {sign_up.style.display = "none";}

    let pay = modal_pay;
    if(pay != null) {pay.style.display = "none";}
}

function doesFileExist(urlToFile) {
    document.write("in doesFileExist")
    let xhr = new XMLHttpRequest();
    xhr.open('HEAD', urlToFile, false);
    xhr.send();

    if (xhr.status == "404") {
        console.log("File doesn't exist");
        return false;
    } else {
        console.log("File exists");
        return true;
    }
}

// ****** Button listeners ***** //

if(sign_up != null) {
    sign_up.addEventListener('click', () => {
        closeAll();
        let modal = document.getElementById("signUp_modal");
        modal.style.display = "block";
        document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
    });
}

if(modal_sign_up != null) {
    modal_sign_up.addEventListener('click', () => {
        closeAll();
        let modal = document.getElementById("signUp_modal");
        modal.style.display = "block";
        document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
    });
}

if(link_download != null) {
    link_download.addEventListener('click', () => {
        // if(isMobile()) {
            let modal = document.getElementById("signUp_modal");
            modal.style.display = "block";
        // }
        // else {
        //     let modal = document.getElementById("download_modal");
        //     modal.style.display = "block";
        // }
        document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
    });
}

if(button_download != null) {
    button_download.addEventListener('click', () => {
        // if(isMobile()) {
            let modal = document.getElementById("signUp_modal");
            modal.style.display = "block";
        // }
        // else {
        //     let modal = document.getElementById("download_modal");
        //     if(modal !== null) {
        //         modal.style.display = "block";
        //     }
        // }
        document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
    });
}

if(close_signUp != null) {
    close_signUp.addEventListener('click', () => {
        let modal = document.getElementById("signUp_modal");
        modal.style.display = "none";
        document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
    });
}

if(close_download != null) {
    close_download.addEventListener('click', () => {
        let modal = document.getElementById('download_modal');
        modal.style.display = "none";
        document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
    });
}

if(close_signIn != null) {
    close_signIn.addEventListener('click', () => {
        let modal = document.getElementById("signIn_modal");
        modal.style.display = "none";
        document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
        link_signIn.style.display = "inline";
    });
}

if(link_signIn != null) {
    link_signIn.addEventListener('click', () => {
        closeAll();
        let modal = document.getElementById("signIn_modal");
        //let span = document.getElementsByClassName("close")[0];
        //document.write('link model.js signin called');
        document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
        modal.style.display = "block";
        link_signIn.style.display = "none";
    });
}

if(link_pwReset != null) {
    link_pwReset.addEventListener('click', () => {
        closeAll();
        let modal = document.getElementById("pwReset_modal");
        modal.style.display = "block";
    });
}

if(close_pwReset != null) {
    close_pwReset.addEventListener('click', () => {
        let pwReset = document.getElementById("pwReset_modal");
        pwReset.style.display = "none";
    });
}


function isMobile() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) || window.innerWidth <= 980 ;
}
