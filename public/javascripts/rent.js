$(document).ready(function () {
  $('#eircode_res').dropdown();

  $('.ui.form.changeTenancy').form({
    fields: {
      eircode_unoccupied: {
        identifier: 'eircode_unoccupied',
        rules: [{
          type: 'empty',
          prompt: 'Select a vacant residence (if available) before pressing button',
        }, ],
      },
    },

    onSuccess: function (event, fields) {
      changeTenancy();
      event.preventDefault();
    },
  });

  function changeTenancy() {
    var formData = $('.ui.form.changeTenancy').serialize();
    let $eircodeNewRental = $('#eircode_res').dropdown('get text');
    let $eircodeOldRental = $('#current_eircode').val(); // Exist rent control
    $('#current_eircode').val($eircodeNewRental); // change Existing rental control
    $.ajax({
      type: 'POST',
      url: '/tenants/changetenancy',
      data: formData,
      success: function (response) {
        CIRCLEMAP.refreshMarkers();
        updateTenantDropdown($eircodeOldRental, $eircodeNewRental);
      },
    });
  }

  function updateTenantDropdown($eircodeOldRental, $eircodeNewRental) {
    let $obj = $('.item.eircode');
    // Remove the selected eircode from dropdown
    for (let i = 0; i < $obj.length; i += 1) {
      if ($obj[i].getAttribute('data-value').localeCompare($eircodeNewRental) == 0) {
        $obj[i].remove();
        $('#eircode_res').dropdown('clear');
        break;
      }
    }

    // Add the new rental eircode to dropdown
    if ($eircodeOldRental != ""){
    	let newMenuItem = dropdownDiv($eircodeOldRental);
    	$('.menu.tenant').append(newMenuItem);
    }
  }

  function dropdownDiv(name) {
    return '<div class="item eircode"' + ' ' + 'data-value="' + name + '">' + name + '</div>';
  }
});
