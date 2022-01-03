$(function () {

    //每5秒刷新
    setInterval(function () {
        window.location.reload();
    }, 10000);


    $(".send").click(function () {
        const chatText = $(".record").val();
        const information = $(".information");
        const receiveId = information.val();
        const sendId = information.prop("id");

        if (chatText.length === 0) {
            alert("发送信息为空!");
            return;
        }
        console.log(chatText);
        console.log(receiveId);
        console.log(sendId);
        $.ajax({
            url: "/sendPersonChatRecord",
            data: {
                chatText: chatText,
                receiveId: receiveId
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
