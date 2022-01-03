$(function () {

    $(".login").click(function () {
        const username = $(".username").val();
        const password = $(".password").val();
        const userRoleDict = $(".userRoleDict").val();

        $.ajax({
            url: "/pre/login",
            data: {
                username: username,
                password: password,
                userRoleDict: userRoleDict
            },
            success: function (obj) {
                if (obj.code === 200) {
                    alert("欢迎登录");
                    if (obj.object.userRoleDict === "TEACHER") {
                        location.href = "/toTeacher/1";
                    } else if (obj.object.userRoleDict === "STUDENT") {
                        location.href = "/toStudent/1";
                    } else if (obj.object.userRoleDict === "ADMIN") {
                        location.href = "/toAdmin/1/1";
                    }
                } else {
                    alert(obj.message + ".....");
                }
            },
            error: function () {
                alert("unknown error");
            }
        })
    })
    $(".register").click(function () {
        location.href = "/pre/toRegister";
    })


})
