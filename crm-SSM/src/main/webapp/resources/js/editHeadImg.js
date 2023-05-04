new Vue({
    el:"#app",
    data:{
        /*定义一个图片的地址*/
        imgUrl:undefined,
    },
    methods:{

        /*文件上传之前*/
        beforeUpload(file) {

            //console.log(file)
            //文件上传大小1m
            let flag=file.size/1024/1024>1
            if(flag) {
                this.$alert("文件上传大小不能超过1m!")
                return false;
            }

        },

        /*文件发生改变时*/
        onChange(file) {
            console.log(file)
            this.imgUrl=URL.createObjectURL(file.raw)
        },

        /*文件上传成功*/
        onSuccess(result) {

            //console.log(result)
            let code=result.code;
            if(code===200) {

                this.$notify({
                    title:"温馨提示",
                    message:"上传成功!",
                    type:"success"
                })


            }else {

                this.$notify({
                    title:"温馨提示",
                    message:"上传失败!"+result.msg,
                    type:"warning"
                })
            }
        },

        /*文件上传确定*/
        uploadHeadImg() {
            this.$refs.uploadImgRef.submit();
        }
    }
})