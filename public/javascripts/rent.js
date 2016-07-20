//$( document ).ready(function() {  
    $('#eircode_res').dropdown();
    
    $('.ui.form.changeTenancy').form({
      fields : {
        eircode_vacancy : {
          identifier : 'eircode_unoccupied',
          rules : [{
            type : 'empty',
            prompt : 'Select a vacant residence (if available) before pressing button'
          },],
        },
      },
      
      onSuccess : function(event, fields) {
        changeTenancy();      
        event.preventDefault();
       },           
    });