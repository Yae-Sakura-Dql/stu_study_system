$(function () {
    $(".change").click(function () {
        const studentIdList = [];
        const groupNameElement = $(".groupName");
        const groupName = groupNameElement.val();
        console.log(groupName);

        const oldGroupName = $(".oldGroupName").val();
        console.log(oldGroupName);
        const teacherId = groupNameElement.prop("id");
        console.log(teacherId);
        $("input:checkbox:checked").each(function (index, element) {
            studentIdList.push($(element).val());
        });
        console.log(studentIdList);
        if (studentIdList.length === 0) {
            alert("请至少选择一个学生");
        } else {
            $.ajax({
                url: "/editGroup",
                type: "post",
                contentType: 'application/json; charset=UTF-8',
                dataType: "json",
                data: JSON.stringify({
                    oldGroupName: oldGroupName,
                    teacherId: teacherId,
                    groupName: groupName,
                    studentIdList: studentIdList
                }),
                success: function (obj) {
                    if (obj.code === 200) {
                        alert("操作成功!")
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
})
