$(function () {

    //每5秒刷新
    setInterval(function () {
        window.location.reload();
    }, 10000);


    $(".send").click(function () {
        const chatText = $(".record").val();
        const information = $(".information");
        const groupName = information.val();
        const sendId = information.prop("id");

        if (chatText.length === 0) {
            alert("发送信息为空!");
            return;
        }
        console.log(chatText);
        console.log(groupName);
        console.log(sendId);
        $.ajax({
            url: "/sendGroupChatRecord",
            data: {
                chatText: chatText,
                receiveName: groupName
            },
            success: function (obj) {
                if (obj.code === 200) {
                    location.href = "/toGroupChatRecord/" + groupName;
                } else {
                    alert(obj.message + ".....");
                }
            },
            error: function () {
                alert("unknown error");
            }
        })
    })


})
