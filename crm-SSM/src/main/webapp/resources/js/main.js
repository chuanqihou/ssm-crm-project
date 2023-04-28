layui.use(["element","jquery"],function(){
    let element = layui.element,
        $ = layui.jquery;

    //给西毒中的导航栏绑定鼠标单击事件
    element.on("nav(menu)",function(d){
        //触发事件的源头，a为dom对象
        let a = d.context;
        let txt = a.innerText;
        /**
         * 1)多级菜单，一级菜单不要生成选项卡
         * 2)对于已经存在的选项卡，点击菜单时，不要重复添加，在原来的基础上获取焦点
         */
        //在中神通中添加新的选项卡
        let idNum = a.id;
        let url = $(a).attr("url");
        //判断当前网页中是否存在lay-id值=idNum
        let len = $("li[lay-id="+idNum+"]").length;
        if(len==0){
            element.tabAdd("zst", {
                title:txt,/*选项卡的标题*/
                id:idNum, /*选项卡的主键*/
                content:"<iframe src='"+url+"' style='width: 100%;'>"
            });
        }
        //让选项卡获取焦点
        element.tabChange("zst",idNum);
        //动态获取高度，设置iframe的高度
        let screenHeight = $(document).height();
        //北丐的高度  100px
        let headerHeight = parseInt($(".layui-header").css("height"));
        //南帝的高度
        let footerHeight = parseInt($(".layui-footer").css("height"));
        //获取选项卡头的高度
        let tabHeaderHeight = parseInt($(".layui-tab-title").css("height"));
        let aviHeight = screenHeight-headerHeight-footerHeight-tabHeaderHeight-50;
        //设置高度
        $(".layui-show>iframe").css("height",aviHeight+"px");

    });
});