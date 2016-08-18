$(document).ready(function () {
  $('.ui.form').form({
    fields: {
      email: {
        identifier: 'email',
        rules: [{
          type: 'empty',
          prompt: 'Pleae insert a email address',
        },],
      },

      password: {
        identifier: 'password',
        rules: [{
          type: 'empty',
          prompt: 'Please enter your password',
        },],
      },
    },
  });
});
