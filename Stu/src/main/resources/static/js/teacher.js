$(function () {


    //多选框全选
    $(".allSelect").click(function () {
        $(".box").prop('checked', $(".allSelect").prop('checked'));
    })

    //全部删除
    $(".allDelete").click(function () {

        //获取被选中的组名
        const groupNameList = [];
        $("input:checkbox[class='box']:checked").each(function (index, element) {
            groupNameList.push($(element).val());
        });
        console.log(groupNameList);


        if (groupNameList.length === 0) {
            alert("未选择组");
        } else {
            $.ajax({
                url: "/deleteBatch",
                type: "post",
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(groupNameList),
                dataType: "json",
                success: function (obj) {
                    if (obj.code === 200) {
                        alert("删除成功");
                        location.href = "/toTeacher/1";
                    } else {
                        alert(obj.message + ".....");
                    }
                },
                error: function () {
                    alert("unknown error");
                }
            })
        }
    })

    //单个删除
    $(".oneDelete").click(function () {

        //获取被选中的组名
        const groupName = [];
        // const Name = $(this).prop('id');
        const Name = $(this).parent('td').parent('tr').find(".box").val();
        groupName.push(Name);
        console.log(groupName);


        $.ajax({
            url: "/deleteBatch",
            type: "post",
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(groupName),
            dataType: "json",
            success: function (obj) {
                if (obj.code === 200) {
                    alert("删除成功");
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
})
