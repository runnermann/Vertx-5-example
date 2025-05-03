/*
 *  Copyright (c) 2020 - 2024 FlashMonkey Inc. .
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

let userApp = angular.module("userApp", ['ngMessages'])
    .controller("StatsController", ["$scope", function ($scope) {

        let isOpen = false;
        let who = "";

        $scope.getAthletics = function (event, hash, subj) {
            event.preventDefault();
            let div = document.getElementById('athletic-details');
            //const user_nx = document.getElementById("user_name_nx").textContent;

            let payload = {
                "hash": hash,
                "subj": subj
            };
            fetch("/Q55/ATTATE001", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
                .then(response => response.text())
                .then(result => {
                    if(result.charAt(0) === '<') {
                        const doc = new DOMParser().parseFromString(result, "text/html");
                        //doc.getElementById("user_name").innerHTML = user_nx;
                        const body = doc.body.firstChild;
                        div.replaceWith(body);

                    } else {
                        alert("Nothing has been posted.");
                    }
                })
        }, function (err) {
            console.log(err.data.error);
        }


        $scope.getElements = function (hash, subj) {
            //let subjLet = subj;
            let div = document.getElementById(subj+'-table');
            const user_nx = document.getElementById("user_name_nx").textContent;

            let payload = {
                "hash": hash,
                "subj": subj
            };
            fetch("/Q55/USTATE022", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
                    .then(response => response.text())
                    .then(result => {
                        if(result.charAt(0) === '<') {
                            const doc = new DOMParser().parseFromString(result, "text/html");
                            doc.getElementById("user_name").innerHTML = user_nx;
                            const body = doc.body.firstChild;
                            div.replaceWith(body);

                            // $scope.openDropdown(subj,"elem");
                        } else {
                            alert("Nothing has been posted.")
                        }
                    })
        }, function (err) {
            console.log(err.data.error);
        }

    $scope.getPositions = function (hash, subj) {
        let div = document.getElementById(subj+'-table');
        const user_nx = document.getElementById("user_name_nx").textContent;
        let payload = {
            "hash": hash,
            "subj": subj
        };

        fetch("/Q55/POTATE066", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(result.charAt(0) === '<') {
                    const doc = new DOMParser().parseFromString(result, "text/html");
                    doc.getElementById("user_name").innerHTML = user_nx;
                    const body = doc.body.firstChild;
                    div.replaceWith(body);

                    // $scope.openDropdown(subj,"pos");
                } else {
                    alert("Nothing has been posted.")
                }
            })
    }, function (err) {
        console.log(err.data.error);
    }

    $scope.getProjects = function (hash, subj) {
        let div = document.getElementById(subj+'-table');
        const user_nx = document.getElementById("user_name_nx").textContent;
        let payload = {
            "hash": hash,
            "subj": subj
        };

        fetch("/Q55/PRTATE055", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(result.charAt(0) === '<') {
                    const doc = new DOMParser().parseFromString(result, "text/html");
                    doc.getElementById("user_name").innerHTML = user_nx;
                    const body = doc.body.firstChild;
                    div.replaceWith(body);

                    // $scope.openDropdown(subj,"proj");
                } else {
                    alert("Nothing has been posted.");
                }
            })
    }, function (err) {
        console.log(err.data.error)
    }

    $scope.getAchievements = function (hash, subj) {
        let div = document.getElementById(subj+'-table');
        const user_nx = document.getElementById("user_name_nx").textContent;
        let payload = {
            "hash": hash,
            "subj": subj
        };

        fetch("/Q55/ASTATE011", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(result.charAt(0) === '<') {
                    const doc = new DOMParser().parseFromString(result, "text/html");
                    doc.getElementById("user_name").innerHTML = user_nx;
                    const body = doc.body.firstChild;
                    div.replaceWith(body);

                    // $scope.openDropdown(subj,"ach");
                } else {
                    alert("Nothing has been posted.")
                }
            })
    }, function (err) {
        console.log(err.data.error);
    }

        $scope.getPeople = function (hash, subj) {
            let div = document.getElementById(subj+'-table');
            const user_nx = document.getElementById("user_name_nx").textContent;

            let payload = {
                "hash": hash,
                "subj": subj
            };

            fetch("/Q55/PSTATE044", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
                .then(response => response.text())
                .then(result => {
                    if(result.charAt(0) === '<') {
                        const doc = new DOMParser().parseFromString(result, "text/html");
                        doc.getElementById("user_name").innerHTML = user_nx;
                        const body = doc.body.firstChild;
                        div.replaceWith(body);

                        // $scope.openDropdown(subj,"peop");
                    } else {
                        alert("Nothing has been posted.");
                    }
                })
        }, function (err) {
            console.log(err.data.error)
        }

    $scope.getKnowledge = function (hash, subj) {
        let div = document.getElementById(subj+'-table');
        const user_nx = document.getElementById("user_name_nx").textContent;
        let payload = {
            "hash": hash,
            "subj": subj
        };

        fetch("/Q55/KSTATE033", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(result.charAt(0) === '<') {
                    const doc = new DOMParser().parseFromString(result, "text/html");
                    doc.getElementById("user_name").innerHTML = user_nx;
                    const body = doc.body.firstChild;
                    div.replaceWith(body);
                    // $scope.openDropdown(subj,"kno");
                } else {
                    alert("Nothing has been posted.");
                }
            })
    }, function (err) {
        console.log(err.data.error())
    }

    // $scope.openDropdown = function (subj, type) {
    //     let dropDown = document.getElementById(subj+'-dropdown');
    //
    //     if(who.match(type) && isOpen) {
    //         dropDown.setAttribute('class', 'container-table dropdown-close');
    //         who = "";
    //         isOpen = false;
    //     }
    //     else {
    //         dropDown.setAttribute('class', 'container-table dropdown-open');
    //         who = type;
    //         isOpen = true;
    //     }
    //
    // }, function (err) {
    //         console.log(err.data.error());
    // }

}]);