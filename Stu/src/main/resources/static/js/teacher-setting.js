$(function () {

    $(".submit").click(function () {
        const id = $(".id").val();
        const nickName = $(".nickName").val();
        const phone = $(".phone").val();
        const email = $(".email").val();

        $.ajax({
            url: "/teacherSetting",
            data: {
                id: id,
                nickName: nickName,
                phone: phone,
                email: email
            },
            success: function (obj) {
                if (obj.code === 200) {
                    alert("修改成功");
                    location.href = "/toTeacher/1";

                } else {
                    alert(obj.message + ".....");
                }
            },
            error: function () {
                alert("unknown error");
            }
        })
    })
    $(".goBack").click(function () {
        location.href = "/toTeacher/1";

    })


})
