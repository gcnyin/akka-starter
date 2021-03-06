# Akka Starter

- First sample
- Ping-pong
- Mars-rover
- Device(SupervisorStrategy)

## Mars Rover

### Requirements

假想你在火星探索团队中负责软件开发。现在你要给登陆火星的探索小车编写控制程序，根据地球发送的控制指令来控制火星车的行动。

火星车收到的指令分为：

- 初始化信息：火星车的降落地点（x, y）和朝向（N, S, E, W）信息

- 移动指令：火星车可以前进（M）,一次移动一格

- 转向指令：火星车可以左转90度（L）或右转90度（R）

由于地球和火星之间的距离很远，指令必须批量发送，火星车执行完整批指令之后，再回报自己所在的位置坐标和朝向。

## Technical Details

Use Actor Model - Akka to write implementation.

## Tasks

- 控制中心发送一批指令，里面只有一条初始化指令，火星车执行完毕后，汇报自己的坐标和朝向；

- 控制中心发送一批指令，里面有一条初始化指令和若干条前进指令，火星车执行完毕后，汇报自己的坐标和朝向；

- 控制中心发送一批指令，里面有一条初始化指令若干条转向指令，火星车执行完毕后，汇报自己的坐标和朝向；

# References

- [Akka actor documents](https://doc.akka.io/docs/akka/current/typed/index.html)
