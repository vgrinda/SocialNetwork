/**
* Created by Oleh on 13.12.2014.
*/

var gitPageApp = angular.module('gitPage', []);

gitPageApp.controller('gitPage', [ '$scope', '$http', function ($scope, $http) {
    $scope.github = {
        empty: true
    };
    $scope.repos = {};
    $scope.ghuser = 'Torvalds';


    //$http.get("sn/user0/getGitlogin").success(function(name) {
        console.log(name);
        $scope.ghuser = name;

        // fetch github data
        $http.get('https://api.github.com/users/' + $scope.ghuser).success( function(data) {
            $scope.github = data;
            $scope.github.empty = false;

            // fetch repos data

            $http.get($scope.github.repos_url).success( function(data) {
                $scope.repos = data;

                function updateOwnerName(j, data) {
                    $scope.repos[j].owner.name = data.name;
                }

                for(var i = 0; i < data.length; i++) {
                    $http.get(data[i].owner.html_url).success( function(data) {
                        updateOwnerName(i, data);
                    });
                }
            });
        });
    //}).error(function(t) {
    //    console.log(t);
    //});


}]);
