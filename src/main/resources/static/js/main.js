$('.close-button').on('click', function () {
    const modalTarget = $(this).closest('.common-modal').attr('id');

    $(`.common-modal`).fadeOut(0);
    setTimeout(function () {
        $(`#${modalTarget}`).modal('hide');
    }, 100);
});