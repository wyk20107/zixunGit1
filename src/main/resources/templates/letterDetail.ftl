<#include "header.ftl" encoding="UTF-8">
    <div id="main">
        <div class="container">
            <ul class="letter-chatlist">
                <#list messages as msg>
                <li id="msg-item-4009580">
                    <div class="tooltip fade right in">
                        <div class="tooltip-arrow"></div>
                        <div class="tooltip-inner letter-chat clearfix">
                            <div class="letter-info">
                                <a href="/user/${(msg.userId)!}/"><img width="32" class="img-circle"
                                                                       src="${(msg.headUrl)!}"></a>
                                <p class="letter-time">${(msg.message.createdDate?string("yyyy-MM-dd HH:mm:ss"))!}</p>
                                <a href="http://127.0.0.1:8080/msg/deleteMessage?conversationId=${(msg.message.conversationId)!}&messageId=${(msg.message.id)!}"
                                   class="nav navbar-nav navbar-right">删除</a>
                            </div>
                            <p class="chat-content">
                                ${(msg.message.content)!}
                            </p>
                        </div>
                    </div>
                </li>
            </#list>
</ul>

        </div>
        <script type="text/javascript">
          $(function(){

            // If really is weixin
            $(document).on('WeixinJSBridgeReady', function() {

              $('.weixin-qrcode-dropdown').show();

              var options = {
                "img_url": "",
                "link": "http://nowcoder.com/j/wt2rwy",
                "desc": "",
                "title": "读《Web 全栈工程师的自我修养》"
              };

              WeixinJSBridge.on('menu:share:appmessage', function (argv){
                WeixinJSBridge.invoke('sendAppMessage', options, function (res) {
                  // _report('send_msg', res.err_msg)
                });
              });

              WeixinJSBridge.on('menu:share:timeline', function (argv) {
                WeixinJSBridge.invoke('shareTimeline', options, function (res) {
                  // _report('send_msg', res.err_msg)
                });
              });

            });

          })
        </script>
    </div>
<#include "footer.ftl" encoding="UTF-8">