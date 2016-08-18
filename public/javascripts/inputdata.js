$(document).ready(function () {
  // initialize the controls in the input data template and validate residence type
  $('.ui.dropdown').dropdown();
  $('.ui.checkbox').checkbox();
  $('.ui.form')
      .form({
        residenceType: {
          identifier: 'residenceType',
          rules: [{
            type: 'empty',
            prompt: 'Please select a residence type',
          }, ],
        },

        rent: {
          identifier: 'rent',
          rules: [{
            type: 'empty',
            prompt: 'Please enter your rent amount',
          }, ],
        },
        
        eircode: {
            identifier: 'eircode',
            rules: [{
              type: 'empty',
              prompt: 'Please enter your eircode',
            }, ],
          },
        
          area: {
              identifier: 'area',
              rules: [{
                type: 'empty',
                prompt: 'Please enter your area',
              }, ],
            },
            
          numberBedrooms: {
              identifier: 'numberBedrooms',
              rules: [{
            	type: 'empty',
                prompt: 'Please enter your bedroom number',
              }, ],
            },
            
            numberBathrooms: {
              identifier: 'numberBathrooms',
              rules: [{
            	type: 'empty',
                prompt: 'Please enter your bathroom number',
              }, ],
            },
            
            
      },

  {
    onSuccess: function () {
      submitForm();
      return false;
    },
  });

  function submitForm() {
    var formData = $('.ui.form.segment input').serialize();
    $.ajax({
      type: 'POST',
      url: '/inputdata/datacapture',
      data: formData,
      success: function (response) {
        console.log('notification: ' + response.inputdata);
        $('#notification').html(response.inputdata);
      },
    });
  }
});
