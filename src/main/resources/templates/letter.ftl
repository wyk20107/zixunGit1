<#include "header.ftl" encoding= "UTF-8">
    <div id="main">
        <div class="container">
            <ul class="letter-list">
<#if conversations?exists>
                <#list conversations as conversation>
                    <li id="conversation-item-10005_622873">
                        <a class="letter-link"
                           href="/msg/detail?conversationId=${(conversation.conversation.conversationId)!}"></a>
                        <div class="letter-info">
                            <span class="l-time">${(conversation.conversation.createdDate?string("yyyy-MM-dd HH:mm:ss"))!}</span>
                        </div>
                        <div class="chat-headbox">
                            <#if (conversation.unread>0)>
                        <span class="msg-num">
                            ${(conversation.unread)!}
                        </span>
                            </#if>
                            <a href="/user/${(conversation.target.id)!}/"><img width="32" class="img-circle"
                                                                               src="${(conversation.target.headUrl)!}"></a>
                        </div>
                        <div class="letter-detail">
                            <a title="${(conversation.target.name)!}" class="letter-name level-color-1">
                                ${(conversation.target.name)!}
                            </a>
                            <p class="letter-brief">
                                <a href="/msg/detail?conversationId=${(conversation.conversation.conversationId)!}">
                                    ${(conversation.conversation.content)!}
                                </a>
                            </p>

                        </div>
                        <div class="l-operate-bar">
                            <br>
                            <a href="/msg/detail?conversationId=${(conversation.conversation.conversationId)!}">
                                <br>共${(conversation.conversation.id)!}条消息
                            </a>
                        </div>
                    </li>

                </#list>
</#if>
            </ul>
            <br>
            <br>
            <form action="/msg/addMessage"method="post" enctype="multipart/form-data">
                <p>发送者id: <input type="text" name="fromId" id="fromId" value=${(user.id)!}></p>
                <p>接收者id: <input type="text" name="toId" id="toId" ></p>
                <p>内容: <input type="text" name="content" id="content" ></p>
                <input type="submit" value="Submit" />
            </form>

<#include "footer.ftl" encoding= "UTF-8">