function adjustDimensions() {
    let vw = window.innerWidth * 0.25;
    let vh = window.innerHeight * 0.25;
    console.log("vw", vw);
    console.log("vh", vh);
    if (vw > vh) {
        document.documentElement.style.setProperty('--scale', `${vw}px`);
    } else if (vh > vw) {
        document.documentElement.style.setProperty('--scale', `${vh}px`);
        // console.log("response")
    } else {
        document.documentElement.style.setProperty('--scale', `${vw}px`);
    }
}

window.addEventListener('DOMContentLoaded', adjustDimensions)
window.addEventListener('resize', adjustDimensions);