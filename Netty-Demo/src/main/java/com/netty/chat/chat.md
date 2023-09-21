## 客户端



## 服务端



## 通信

通信消息接口：客户端与服务端相互通信内容。

## session 

记录用户登录状态，用户登录成功后生成session信息。

session简单基于内存实现，当用户登录成功之后，将 ‘用户名-channel’ 设置到sessionMap中
提供方法：

- 设置session: save()

- 获取session: getSession(username)

- 移除session: removeSession(username) 


## 协议

魔数 四字节

版本号 一字节

