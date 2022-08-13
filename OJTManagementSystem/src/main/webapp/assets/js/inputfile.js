/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




// input file

       const inputFile = document.getElementById("inputFile");
       const display = document.getElementById("displayResult");
       if(display.innerHTML ==""){
           display.innerHTML="No File Chosen."
       }
       inputFile.addEventListener("change", function(){
        if (inputFile.value){
            display.innerHTML = inputFile.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
        } 
       })