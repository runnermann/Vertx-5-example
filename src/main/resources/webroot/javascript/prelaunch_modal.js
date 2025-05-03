'use strict';

let modal_sign_up = document.getElementById("link_modal_signUp");
let link_download = document.getElementById("link_download");
let close_signUp = document.getElementById("close_signUp");


if(link_download != null) {
  link_download.addEventListener('click', () => {

    let modal = document.getElementById("signUp_modal");
    modal.style.display = "block";

    // document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
  });
}

if(modal_sign_up != null) {
  modal_sign_up.addEventListener('click', () => {
    closeAll();
    let modal = document.getElementById("signUp_modal");
    modal.style.display = "block";
    // document.body.style = "overflow-y:hidden; position: relative; margin-right: var(--widthReflow);";
  });
}

if(close_signUp != null) {
  close_signUp.addEventListener('click', () => {
    let modal = document.getElementById("signUp_modal");
    modal.style.display = "none";
    // document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
  });
}
