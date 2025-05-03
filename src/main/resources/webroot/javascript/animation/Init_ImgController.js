//import Image_Typewriter from "./Image_Typewriter";
import ImagesController_MilService from "./controller/ImagesController_MilService.js";
import ImagesController_Profile from "./controller/ImagesController_Profile.js";
import ImagesController_Athlete from "./controller/ImagesController_Athlete.js";

class profileService {
    constructor() {
        // Mil and Service image uploader
        this.mil_ImageButton = document.getElementById("edit_milsvc_imgs");
        if (this.mil_ImageButton) {
            this.mil_ImageButton.onclick = () => {
                this.controller_1 = new ImagesController_MilService();
                this.controller_1.startMilSvcImgTypewriter();
            };
        } else {
            console.error("Failed to find element with ID 'edit_milsvc_imgs'");
        }
        // Profile image uploader
        this.pro_ImageButton = document.getElementById("edit_main_imgs");
        if (this.pro_ImageButton) {
            this.pro_ImageButton.onclick = () => {
                this.controller_2 = new ImagesController_Profile();
                this.controller_2.startProfImgTypewriter();
            };
        } else {
            console.error("Failed to find element with ID 'edit_athlete_imgs'");
        }
        // Athlete image button
        this.ath_ImageButton = document.getElementById("edit_athlete_imgs");
        if (this.ath_ImageButton) {
            this.ath_ImageButton.onclick = () => {
                this.controller_3 = new ImagesController_Athlete();
                this.controller_3.startAthleteImgTypewriter();
            };
        } else {
            console.error("Failed to find element with ID 'edit_athlete_imgs'");
        }
    }
}

// Initialize the FormController once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new profileService();
});




