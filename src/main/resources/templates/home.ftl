<#include "header.ftl" encoding="UTF-8">


    <div id="main">
        <#assign x = "xxxx-xx-xx">
    <#list vos as vo>
        <#if x != vo.news.createdDate?string("yyyy-MM-dd")>
            <#assign x = vo.news.createdDate?string("yyyy-MM-dd")>
        <div class="container" id="daily">
            <div class="jscroll-inner">
                <div class="daily">
                </div>

                <h3 class="date">
                    <i class="fa icon-calendar"></i>
                    <span>News At ${(vo.news.createdDate?string("yyyy-MM-dd"))!}</span>
                </h3>


        </#if>
                    <div class="posts">

                        <div class="post">
                            <div class="votebar">
                                <#if (vo.like > 0)>
                                <button class="click-like up pressed" data-id="${(vo.news.id)!}" title="赞同"><i
                                        class="vote-arrow"></i><span
                                        class="count">${(vo.news.likeCount)!}</span></button>
                                <#else>
                                <button class="click-like up" data-id="${(vo.news.id)!}" title="赞同"><i
                                        class="vote-arrow"></i><span
                                        class="count">${(vo.news.likeCount)!}</span></button>
                                </#if>

                                <#if (vo.like < 0)>
                                <button class="click-dislike down pressed" data-id="${(vo.news.id)!}" title="反对"><i
                                        class="vote-arrow"></i>
                                </button>
                                <#else>
                                <button class="click-dislike down" data-id="${(vo.news.id)!}" title="反对"><i
                                        class="vote-arrow"></i>
                                </button>
                                </#if>
                            </div>
                            <div class="content" data-url="${(vo.news.link)!}">
                                <div>
                                    <a href="${(vo.news.image)!}">
                                        <img class="content-img" src="${(vo.news.image)!}" alt="查看图片">
                                </div>
                                <div class="content-main">
                                    <h3 class="title">
                                        <a target="_blank" rel="external nofollow"
                                           href="/news/${(vo.news.id)!}">${(vo.news.title)!}</a>

                                    </h3>
                                    <div class="meta">
                                        ${(vo.news.link)!}
                                        <span>
                                            <i class="fa icon-comment"></i> ${(vo.news.commentCount)!}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="user-info">
                                <div class="user-avatar">
                                    <a href="/user/${(vo.user.id)!}/"><img width="32" class="img-circle"
                                                                           src="${(vo.user.headUrl)!}"></a>
                                </div>

                            </div>

                            <div class="subject-name">来自 <a href="/user/${(vo.user.id)!}/">${(vo.user.name)!}</a></div>
                        </div>
                        <#if vo_has_next == false>
                            <#break>
                        </#if>

                    </div>
    </#list>

    </div>
    </div>
    </div>

<div style="text-align: center;">
    <ul class="pagination">
        <script>
            for (i = ${(pageIndex)!}-5; i < ${(pageIndex)!}+5;++i) {
                    if (i < 0){
                        continue;
                    }
                 url = "http://127.0.0.1:8080/index?pageIndex=" + i;
            document.write("<li><a href=\"" + url + "\">" + (i + 1) + "</a></li>")
            }
        </script>
</div>
${(count)!}/${(pageSize)!}





<#include "footer.ftl" encoding="UTF-8">