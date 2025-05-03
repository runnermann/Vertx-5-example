

'use strict';

var resApp = angular.module("resetApp", [])
    .controller("resetController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {

        const EMTPY_STRING = "";
        let user_email = "";

        $scope.newUser = function () {
            $scope.userName = EMTPY_STRING;
            $scope.email = EMTPY_STRING;
            $scope.verifyPassword = EMTPY_STRING;
            $scope.code = EMTPY_STRING;
            user_email = EMTPY_STRING;
        };

        $scope.userExists = function () {
            return $scope.userId !== undefined;
        };

        $scope.save = function (isValid) {

            //console.log("save called");
            if($scope.userName === "") {
                $scope.error("Please check your name.");
            }
            else if($scope.email === "") {
                $scope.error("Please check your email.");
            }
            else if ($scope.password !== $scope.verifyPassword) {
                $scope.error("Passwords do not match.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {

                let payload = {
                    "userName": $scope.userName,
                    "code": $scope.code,
                    "email": $scope.email,
                    "password": $scope.password
                };
                $http.post("/api/reset", payload).then(function (ok) {
                    $scope.success("User saved");
                }, function (err) {
                    $scope.error(err.data.error);
                });
            }
        };

        // *** SUCCESS FAILURE MESSAGES/ACTIONS ***
        // Tell user they were successfully added.
        $scope.success = function (message) {
            $scope.alertMessage = message;
            let alert = document.getElementById("alertMessage");
            alert.classList.add("alert-success");
            alert.classList.remove("invisible");
            $timeout(function () {
                alert.classList.add("invisible");
                alert.classList.remove("alert-success");
            }, 10000);

            let response = document.getElementById("responseAction")
            response.classList.add("alert-success");
        };

        // Tell the user that their email already exists and provide the
        // log-in link and password reset link.
        $scope.error = function (message) {
            $scope.alertMessage = message;
            let alert = document.getElementById("alertMessage");
            alert.classList.add("alert-danger");
            alert.classList.remove("invisible");
            $timeout(function () {
                alert.classList.add("invisible");
                alert.classList.remove("alert-danger");
            }, 10000);

            let response = document.getElementById("responseAction");
            reponse.classList.add("alert-danger");
        };

        // *** RENDERING PROMISE ***

        let emailRenderingPromise = null;
        $scope.$watch("email", function (text) {
            //if (eb.state !== EventBus.OPEN) return;
            if (emailRenderingPromise !== null) {
                $timeout.cancel(emailRenderingPromise);
            }
            emailRenderingPromise = $timeout(function () {
                emailRenderingPromise = null;
                // tag::eventbus-user-sender[]
                eb.send("app.user", text, function (err, reply) { // <1>
                    if (err === null) {
                        $scope.$apply(function () { // <2>
                            $scope.updateRendering(reply.body); // <3>
                        });
                    } else {
                        console.warn("Error rendering user content: " + JSON.stringify(err));
                    }
                });
                // end::eventbus-user-sender[]
            }, 300);
        });

    }]);

// refer to https://stackoverflow.com/questions/14012239/password-check-directive-in-angularjs
// comments made by Gill Bates
// and http://jsfiddle.net/gUSZS/
resApp.directive('nxEqualEx', function() {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, model) {
            if (!attrs.nxEqualEx) {
                console.error('nxEqualEx expects a model as an argument!');
                return;
            }
            scope.$watch(attrs.nxEqualEx, function (value) {
                // Only compare values if the second ctrl has a value.
                if (model.$viewValue !== undefined && model.$viewValue !== '') {
                    model.$setValidity('nxEqualEx', value === model.$viewValue);
                }
            });
            model.$parsers.push(function (value) {
                // Mute the nxEqual error if the second ctrl is empty.
                if (value === undefined || value === '') {
                    model.$setValidity('nxEqualEx', true);
                    return value;
                }
                var isValid = value === scope.$eval(attrs.nxEqualEx);
                model.$setValidity('nxEqualEx', isValid);
                return isValid ? value : undefined;
            });
        }
    };
});
//function UserController($scope) {}