<#include "header.ftl" encoding= "UTF-8">
    <div id="main">
        <div class="container">
            <div class="post detail">

                <div class="votebar">
                    <#if (like>0)>
                    <button class="click-like up pressed" aria-pressed="false" title="赞同"><i
                            class="vote-arrow"></i><span
                            class="count">${(news.likeCount)!}</span></button>
                    <#else>
                        <button class="click-like up" aria-pressed="false" title="赞同"><i class="vote-arrow"></i><span
                                class="count">${(news.likeCount)!}</span></button>
                    </#if>
                    <#if (like<0)>
                        <button class="click-dislike down pressed" aria-pressed="true" title="反对"><i
                                class="vote-arrow"></i>
                        </button>
                    <#else>
                    <button class="click-dislike down" aria-pressed="true" title="反对"><i class="vote-arrow"></i>
                    </button>
                    </#if>
                </div>

                <div class="content" data-url="${(news.link)!}">
                    <div class="content-img">

                        <a href="${(news.image)!}">
                            <img class="content-img" src="${(news.image)!}" alt="查看图片">
                    </div>
                    <div class="content-main">
                        <h3 class="title">
                            <a target="_blank" rel="external nofollow" href="${(news.link)!}">${(news.title)!}</a>
                        </h3>
                        <div class="meta">
                        ${(news.link)!}
                            <span>
                                  <i class="fa icon-comment"></i> ${(news.commentCount)!}
                              </span>
                        </div>
                    </div>
                </div>
                <div class="user-info">
                    <div class="user-avatar">
                        <a href="/user/${(owner.id)!}"><img width="32" class="img-circle"
                                                            src="${(owner.headUrl)!}"></a>
                    </div>
                </div>

                <div class="subject-name">来自 <a href="/user/${(owner.id)!}">${(owner.name)!}</a></div>
            </div>

            <div class="post-comment-form">
                <#if user?exists>
                    <span>评论 (${(news.commentCount)!})</span>
                    <form method="post" action="/addComment">
                        <div class="form-group text required comment_content">
                            <label class="text required sr-only">
                                <abbr title="required">*</abbr> 评论
                            </label>
                            <input type="hidden" name="newsId" value="${(news.id)!}"/>
                            <textarea rows="5" class="text required comment-content form-control" name="content"
                                      id="content"></textarea>
                        </div>
                        <div class="text-right">
                            <input type="submit" name="commit" value="提 交" class="btn btn-default btn-info">
                        </div>
                    </form>
                <#else>
                <div class="login-actions">
                    <a class="js-login" href="javascript:void(0);">->登录后评论<-</a>
                </div>
                </#if>
            </div>

            <div id="comments" class="comments">
<#list comments as commentvo>
    <div class="media">
        <a class="media-left" href="/user/${(commentvo.user.id)!}">"
            <img src="${(commentvo.user.headUrl)!}">
        </a>
        <div class="media-body">
            <h4 class="media-heading">
                <small class="date">${(commentvo.comment.createdDate?string("yyyy-MM-dd HH:mm:ss"))!}</small>
            </h4>
            <div>${(commentvo.comment.content)!}</div>
            <br>
        </div>
                    <#if user?exists>
                        <#if user.id=commentvo.comment.userId>
                    <a href="http://127.0.0.1:8080/deleteComment?commentId=${(commentvo.comment.id)!}&newsId=${(news.id)!}"
                       class="nav navbar-nav navbar-right"><i class="fa icon-trash"></i>删除</a>
                        </#if>
                    </#if>

    </div>
    <br>
</#list>
            </div>

        </div>

    </div>

<#include "footer.ftl" encoding= "UTF-8">