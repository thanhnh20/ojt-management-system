/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// navigation 
var page = document.getElementById("pageX").innerHTML;
var navigation = document.getElementsByClassName("page-link");

for (var i = 0; i < navigation.length; i++) {
    navigation[i].className = navigation[i].className.replace(" pagination-active", "");
 // var current = document.getElementsByClassName("pagination-active");
  //current[0].className = current[0].className.replace(" pagination-active", "");
  //this.className += " pagination-active";
 // });   
}

navigation[page-1].className += " pagination-active";
