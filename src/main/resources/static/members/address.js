function openZipSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            $('[name=zipcode]').val(data.zonecode); // 우편번호 (5자리)
            $('[name=street]').val(data.address);
            $('[name=detail]').val(data.buildingName);
        }
    }).open();
}