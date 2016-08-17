$('.ui.fluid.form.segment').form({
  fields : {
    email : {
      identifier : 'email',
      rules : [{
        type : 'empty',
        prompt : 'Pleas enter email to login'
      }]
    };
}
})
