$( document ).ready(function() {
  // initialize the controls in the input data template and validate residence type
  $('.ui.dropdown').dropdown();
  $('.ui.checkbox').checkbox();
  $('.ui.form')
  .form({
      residenceType : {
          identifier : 'residence.residenceType',
          rules: [{
                    type : 'empty',
                    prompt: 'Please select a residence type'
            }]
      }
  },

  {
      onSuccess : function() {
          submitForm();
          return false;
      } 
  });

  function submitForm() {
      var formData = $('.ui.form.segment input').serialize(); 
      $.ajax({
        type: 'POST',
        url: '/inputdata/datacapture',
        data: formData,
        success: function(response) { 
            console.log("notification: " + response.inputdata);
            $('#notification').html(response.inputdata);
        }
      });
    }
});