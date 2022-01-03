$(function () {


    $(".submit").click(function () {
        const id = $(".id").val();
        const username = $(".username").val();
        const password = $(".password").val();
        const nickName = $(".nickName").val();
        const userRoleDict = $(".userRoleDict").val();
        const classroom = $(".classroom").val();
        const teacherId = $(".selectedTeacher option:selected").val();

        $.ajax({
            url: "/adminSetting",
            data: {
                id: id,
                username: username,
                password: password,
                userRoleDict: userRoleDict,
                nickName: nickName,
                classroom: classroom,
                teacherId: teacherId
            },
            success: function (obj) {
                if (obj.code === 200) {
                    alert("修改成功");
                    location.href = "/toAdmin/1/1";
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
        location.href = "/toAdmin/1/1";
    })


})
